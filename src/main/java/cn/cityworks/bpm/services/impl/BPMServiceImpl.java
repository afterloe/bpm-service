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
    public Object myTask(String token) {
        ResponseDTO<UserVO> response = receptionCenterClient.who(token);
        if (200 != response.getCode()) {
            throw BasicException.build(response.getMsg(), response.getCode());
        }
        return taskService.createTaskQuery()
                .taskAssigneeLike(response.getData().getId()).list().stream()
                .map(task -> {
                    Map repo = new LinkedHashMap();
                    repo.put("name", task.getName());
                    repo.put("id", task.getId());
                    repo.put("processInstanceId", task.getProcessInstanceId());
                    repo.put("assignee", task.getAssignee());
                    repo.put("createTime", task.getCreateTime());
                    repo.put("description", task.getDescription());
                    repo.put("owner", task.getOwner());
                    repo.put("formDate", getTaskSummary(task.getId()));
                    return repo;
                }).collect(toList());
    }

    private StringBuffer getFormData(File file) {
        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
            String line = null;
            while (null != (line = bufferedReader.readLine())) {
                stringBuffer.append(line);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return stringBuffer;
        }
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

    private Object getTaskSummary(String taskId) {
        return formService.getTaskFormData(taskId).getFormProperties()
                .stream().filter(formProperty -> !formProperty.getId().equals("describe"))
                .collect(toList());
    }

    @Override
    public Object listTask(String access_token) {
        ResponseDTO<UserVO> response = receptionCenterClient.who(access_token);
        if (200 != response.getCode()) {
            throw BasicException.build(response.getMsg(), response.getCode());
        }
        Map result = userClient.listGroupsByUserId(response.getData().getId(), 0, 200);
        if (HttpStatus.SC_OK != Integer.valueOf(result.get("code").toString())) {
            throw BasicException.build("can't find any groups info", HttpStatus.SC_INTERNAL_SERVER_ERROR);
        }
        Map data = (Map) result.get("data");
        List<Map> groups = (List) data.get("content");
        // 获取 所在组内的所有任务列表
        groups.stream().forEach(group -> {
            List<Task> doingTaskList = taskService.createTaskQuery()
                    .taskCandidateGroup(group.get("groupName").toString()).list();
            group.put("toDo", doingTaskList.stream().map(task -> {
                Map repo = new LinkedHashMap();
                repo.put("name", task.getName());
                repo.put("id", task.getId());
                repo.put("processInstanceId", task.getProcessInstanceId());
                repo.put("assignee", task.getAssignee());
                repo.put("createTime", task.getCreateTime());
                repo.put("description", task.getDescription());
                repo.put("owner", task.getOwner());
                repo.put("formDate", getTaskSummary(task.getId()));
                return repo;
            }).collect(toList()));
        });
        return groups;
    }

    @Override
    public Object getTaskForm(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (null == task) {
            throw BasicException.build("task not found!");
        }
        if (null != task.getFormKey()) {
            return formService.getRenderedTaskForm(task.getId()).toString();
        } else {
            return task.getTaskLocalVariables();
        }
//        TaskFormData taskFormData = formService.getTaskFormData(taskId);
//      return taskFormData.getFormProperties().stream().map(handlerFormProperty).collect(toList());
    }

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
