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
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * create by afterloe on 2017/10/20
 */
@Service
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

    @Override
    public Object listByBusinessKey(String businessKey) {
        List list = runtimeService.createProcessInstanceQuery()
                .processInstanceBusinessKey(businessKey).list();
        if (0 == list.size()) {
            list = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceBusinessKey(businessKey).list();
        }
        return list.stream().map(processInstance -> printProcess(processInstance)).collect(toList());
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
            result.put("activeTaskInfo", cn.cityworks.bpm.services.Task.toString.apply(task));
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
            result.put("durationInMillis", instance.getDurationInMillis());
            result.put("processVariables", instance.getProcessVariables());
            return result;
        }
        throw BasicException.build("this type of process is not support.");
    }
}
