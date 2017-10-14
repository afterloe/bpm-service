package cn.cityworks.bpm.demo.services.impl;

import cn.cityworks.bpm.demo.exceptions.BasicException;
import cn.cityworks.bpm.demo.services.BPMService;
import org.activiti.engine.FormService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @Override
    public Object getProcessFormDataByStart(String processId) {
        return formService.getStartFormData(processId).getFormProperties();
    }

    @Override
    public Object startProcess(String processId, Map formData, String access_token) {
        TaskFormData taskFormData = formService.getTaskFormData(processId);
        if (null == taskFormData) {
            throw BasicException.build("no such this task!.", HttpStatus.NOT_FOUND.value());
        }
        List<FormProperty> items = taskFormData.getFormProperties();
        Map<String, String> variables = new HashMap<>();
        FormProperty lackParameter = items.stream()
                .filter(item -> item.isRequired() && !formData.containsKey(item.getId()))
                .findAny()
                .orElse(null);
        if (null != lackParameter) {
            throw BasicException.build("lack parameter -> " + lackParameter.getId(),
                    HttpStatus.BAD_REQUEST.value());
        }
        items.stream().filter(FormProperty::isWritable).forEach(item -> {
            String key = item.getId();
            variables.put(key, formData.get(key).toString());
        });
        Object data = formService.submitStartFormData(processId, variables);
        if (null == data) {
            throw BasicException.build("create task failed!");
        }
        return true;
    }

    @Override
    public Object listTask(String token) {
        Map<String, List<Task>> listTask = new HashMap<>();
        // 获取 直接分配给当前人或已签收的任务
        List<Task> doingTaskList = taskService.createTaskQuery().taskAssignee(token).list();
        // 等待签收的任务
        List<Task> waitingClaimTaskList = taskService.createTaskQuery().taskCandidateUser(token).list();
        listTask.put("doingTaskList", doingTaskList);
        listTask.put("waitingClaimTaskList", waitingClaimTaskList);
        return listTask;
    }

    @Override
    public Object getTaskForm(String taskId) {
        TaskFormData data = formService.getTaskFormData(taskId);
        return data.getFormProperties();
    }

    @Override
    public Object completeTask(String token, Map formData) {
        if (!formData.containsKey("taskId")) {
            throw BasicException.build("Lack parameter -> taskId", HttpStatus.BAD_REQUEST.value());
        }
        String taskId = formData.get("taskId").toString();
        TaskFormData taskFormData = formService.getTaskFormData(taskId);
        if (null == taskFormData) {
            throw BasicException.build("no such this task!.", HttpStatus.NOT_FOUND.value());
        }
        List<FormProperty> items = taskFormData.getFormProperties();
        Map<String, String> variables = new HashMap<>();
        FormProperty lackParameter = items.stream()
                .filter(item -> item.isRequired() && !formData.containsKey(item.getId()))
                .findAny()
                .orElse(null);
        if (null != lackParameter) {
            throw BasicException.build("lack parameter -> " + lackParameter.getId(),
                    HttpStatus.BAD_REQUEST.value());
        }
        items.stream().filter(FormProperty::isWritable).forEach(item -> {
            String key = item.getId();
            variables.put(key, formData.get(key).toString());
        });
        formService.submitTaskFormData(taskId, variables);
        return true;
    }
}
