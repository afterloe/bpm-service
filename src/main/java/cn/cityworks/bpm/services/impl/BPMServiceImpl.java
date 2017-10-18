package cn.cityworks.bpm.services.impl;

import cn.cityworks.bpm.domain.ResponseDTO;
import cn.cityworks.bpm.domain.UserVO;
import cn.cityworks.bpm.exceptions.BasicException;
import cn.cityworks.bpm.integrate.ReceptionCenterClient;
import cn.cityworks.bpm.integrate.UserClient;
import cn.cityworks.bpm.services.BPMService;
import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.FormType;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.task.Task;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * create by afterloe on 2017/10/13
 */
@Service
public class BPMServiceImpl implements BPMService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BPMServiceImpl.class);

    @Autowired
    private FormService formService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private ReceptionCenterClient receptionCenterClient;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private UserClient userClient;

    @Override
    public Object completeTask(String access_token, String taskId, Map formData) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (null == task) {
            throw BasicException.build("no such this task", HttpStatus.SC_NOT_FOUND);
        }
        ResponseDTO<UserVO> response = receptionCenterClient.who(access_token);
        if (200 != response.getCode()) {
            throw BasicException.build(response.getMsg(), response.getCode());
        }
        if (null == task.getAssignee()) {
            taskService.claim(taskId, response.getData().getId());
        }
        TaskFormData taskFormData = formService.getTaskFormData(taskId);
        List<FormProperty> items = taskFormData.getFormProperties();
        Map<String, String> variables = new HashMap<>();
        FormProperty lackParameter = items.stream()
                .filter(item -> item.isRequired() && !formData.containsKey(item.getId()))
                .findAny().orElse(null);
        if (null != lackParameter) {
            throw BasicException.build("lack parameter -> " + lackParameter.getId()
                    , HttpStatus.SC_BAD_REQUEST);
        }
        items.stream().filter(FormProperty::isWritable).forEach(item -> {
            String key = item.getId();
            variables.put(key, formData.get(key).toString());
        });
        formService.submitTaskFormData(taskId, variables);
        return true;
    }
}
