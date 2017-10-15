package cn.cityworks.bpm.demo.services.impl;

import cn.cityworks.bpm.demo.domain.ResponseDTO;
import cn.cityworks.bpm.demo.domain.UserVO;
import cn.cityworks.bpm.demo.exceptions.BasicException;
import cn.cityworks.bpm.demo.integrate.ReceptionCenterClient;
import cn.cityworks.bpm.demo.services.BPMService;
import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Object getFromDataList(String processId) {
        return formService.getStartFormData(processId).getFormProperties();
    }

    @Override
    public Object reportSupervisionIncident(String processId, Map formData, String access_token) {
        ResponseDTO<UserVO> response = receptionCenterClient.who(access_token);
        if (200 != response.getCode()) {
            throw BasicException.build(response.getMsg(), response.getCode());
        }

        StartFormData taskFormData = formService.getStartFormData(processId);
        if (null == taskFormData) {
            throw BasicException.build("no such this process!.", HttpStatus.SC_NOT_FOUND);
        }
        List<FormProperty> items = taskFormData.getFormProperties();
        Map<String, String> variables = new HashMap<>();
        FormProperty lackParameter = items.stream()
                .filter(item -> item.isRequired() && !formData.containsKey(item.getId()))
                .findAny()
                .orElse(null);
        if (null != lackParameter) {
            throw BasicException.build("lack parameter -> " + lackParameter.getId(),
                    HttpStatus.SC_BAD_REQUEST);
        }
        items.stream().filter(FormProperty::isWritable).forEach(item -> {
            String key = item.getId();
            variables.put(key, formData.get(key).toString());
        });
        identityService.setAuthenticatedUserId(response.getData().getId()); // 设置发起流程的用户
        Object data = formService.submitStartFormData(processId, variables);
        if (null == data) {
            throw BasicException.build("create task failed!", HttpStatus.SC_INTERNAL_SERVER_ERROR);
        }
        return true;
    }

    @Override
    public Object listTask(String access_token) {
        ResponseDTO<UserVO> response = receptionCenterClient.who(access_token);
        if (200 != response.getCode()) {
            throw BasicException.build(response.getMsg(), response.getCode());
        }
        Map<String, List<Task>> listTask = new HashMap<>();
        // 获取 直接分配给当前人或已签收的任务
        List<Task> doingTaskList = taskService.createTaskQuery().taskCandidateGroup("区级河长").list();
        return doingTaskList.stream().map(task -> {
            Map repo = new LinkedHashMap();
            repo.put("name", task.getName());
            repo.put("id", task.getId());
            repo.put("processInstanceId", task.getProcessInstanceId());
            repo.put("assignee", task.getAssignee());
            repo.put("createTime", task.getCreateTime());
            repo.put("description", task.getDescription());
            repo.put("localVariables", task.getTaskLocalVariables());
            repo.put("owner", task.getOwner());
            repo.put("processVariables", task.getProcessVariables());
            return repo;
        }).collect(toList());
    }

    @Override
    public Object getTaskForm(String taskId) {
        TaskFormData data = formService.getTaskFormData(taskId);
        return data.getFormProperties();
    }

    @Override
    public Object completeTask(String token, Map formData) {
        if (!formData.containsKey("taskId")) {
            throw BasicException.build("Lack parameter -> taskId", HttpStatus.SC_BAD_REQUEST);
        }
        String taskId = formData.get("taskId").toString();
        TaskFormData taskFormData = formService.getTaskFormData(taskId);
        if (null == taskFormData) {
            throw BasicException.build("no such this task!.", HttpStatus.SC_NOT_FOUND);
        }
        List<FormProperty> items = taskFormData.getFormProperties();
        Map<String, String> variables = new HashMap<>();
        FormProperty lackParameter = items.stream()
                .filter(item -> item.isRequired() && !formData.containsKey(item.getId()))
                .findAny()
                .orElse(null);
        if (null != lackParameter) {
            throw BasicException.build("lack parameter -> " + lackParameter.getId(),
                    HttpStatus.SC_BAD_REQUEST);
        }
        items.stream().filter(FormProperty::isWritable).forEach(item -> {
            String key = item.getId();
            variables.put(key, formData.get(key).toString());
        });
        formService.submitTaskFormData(taskId, variables);
        return true;
    }
}
