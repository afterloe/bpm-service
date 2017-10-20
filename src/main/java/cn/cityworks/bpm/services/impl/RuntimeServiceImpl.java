package cn.cityworks.bpm.services.impl;

import cn.cityworks.bpm.exceptions.BasicException;
import cn.cityworks.bpm.services.History;
import cn.cityworks.bpm.services.Runtime;
import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * create by afterloe on 2017/10/17
 */
@Service
public class RuntimeServiceImpl implements Runtime {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private History historyImpl;

    @Override
    public Object startProcess(Map processData) {
        checkedParameter(processData, "starter", "processDefinitionKey", "businessKey");
        identityService.setAuthenticatedUserId(processData.get("starter").toString()); // 设置发起流程的用户
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(
                processData.get("processDefinitionKey").toString() ,processData.get("businessKey").toString());
        if (null == instance) {
            throw BasicException.build("start process failed");
        }
        Map result = new LinkedHashMap();
        Task task = taskService.createTaskQuery().processInstanceId(instance.getProcessInstanceId())
                .active().singleResult();
        result.put("type", "activeProcess");
        result.put("businessKey", instance.getBusinessKey());
        result.put("name", instance.getName());
        result.put("id", instance.getId());
        result.put("version", instance.getProcessDefinitionVersion());
        result.put("definitionName", instance.getProcessDefinitionName());
        result.put("definitionId", instance.getProcessDefinitionId());
        result.put("processId", instance.getProcessInstanceId());
        result.put("activeTaskId", task.getId());
        result.put("activeTaskInfo", cn.cityworks.bpm.services.Task.toString.apply(task));

        return result;
    }

    @Override
    public Object listActive(int page, int number) {
        return runtimeService.createProcessInstanceQuery().active().list().stream().map(p -> {
            Map m = new LinkedHashMap<>();
            m.put("id", p.getProcessDefinitionId());
            m.put("processDefinitionName", p.getProcessDefinitionName());
            m.put("processInstanceId", p.getProcessInstanceId());
            m.put("name", p.getName());
            return m;
        }).collect(toList());
    }
}
