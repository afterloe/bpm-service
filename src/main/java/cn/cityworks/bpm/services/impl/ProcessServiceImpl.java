package cn.cityworks.bpm.services.impl;

import cn.cityworks.bpm.exceptions.BasicException;
import cn.cityworks.bpm.services.Process;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * create by afterloe on 2017/10/20
 */
public class ProcessServiceImpl implements Process {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;

    @Override
    public Object getProcess(String processId) {
        ProcessInstance instance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processId).singleResult();
        if (null == instance) {
            return getHistoryProcess(processId);
        }
        return printProcess(instance);
    }

    private Object getHistoryProcess(String processId) {
        HistoricProcessInstance instance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processId).singleResult();
        if (null == instance) {
            throw BasicException.build("no such this process instance or this process is over. -> " + processId
                    , HttpStatus.SC_NOT_FOUND);
        }
        return printProcess(instance);
    }

    private Map printProcess(Object processInstance) {
        if (processInstance instanceof ProcessInstance) {
            Map result = new LinkedHashMap();
            ProcessInstance instance = (ProcessInstance) processInstance;
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
            result.put("activeTaskInfo", toString.apply(task));
            return result;
        }

        return printHistoryInstance(processInstance);
    }

    private Map printHistoryInstance(Object processInstance) {
        if (processInstance instanceof HistoricProcessInstance) {
            Map result = new LinkedHashMap();
            HistoricProcessInstance instance = (HistoricProcessInstance) processInstance;
            result.put("type", "historyProcess");
            result.put("businessKey", instance.getBusinessKey());
            result.put("name", instance.getName());
            result.put("id", instance.getId());
            result.put("definitionId", instance.getProcessDefinitionId());
            result.put("processId", instance.getId());
            result.put("startTime", instance.getStartTime());
            result.put("endTime", instance.getEndTime());
            result.put("workerTime", instance.getDurationInMillis());
            result.put("claimTime", instance.getProcessVariables());
            return result;
        }
        throw BasicException.build("this type of process is not support.");
    }

    /**
     *  Task 转换为 普通Result VO对象
     */
    private Function<Task, Map> toString = task -> {
        Map result = new LinkedHashMap();
        result.put("name", task.getName());
        result.put("assignee", task.getAssignee());
        result.put("owner", task.getOwner());
        result.put("description", task.getDescription());
        result.put("createTime", task.getCreateTime());
        return result;
    };
}
