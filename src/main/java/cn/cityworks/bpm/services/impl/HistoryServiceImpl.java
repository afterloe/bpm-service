package cn.cityworks.bpm.services.impl;

import cn.cityworks.bpm.services.History;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class HistoryServiceImpl implements History {

    private static final Logger LOGGER = LoggerFactory.getLogger(HistoryServiceImpl.class);

    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;

    @Override
    public Object getProcess(String processId) {
        Task task = taskService.createTaskQuery().processInstanceId(processId).active().singleResult(); // 当前任务
        List<HistoricTaskInstance> historyTaskList = historyService.createHistoricTaskInstanceQuery().processInstanceId(task.getProcessInstanceId()).list();
        return historyTaskList.stream().map(historicTask -> {
            Map result = new LinkedHashMap();
            result.put("startTime", historicTask.getStartTime());
            result.put("endTime", historicTask.getEndTime());
            result.put("assignee", historicTask.getAssignee());
            result.put("workerTime", historicTask.getWorkTimeInMillis());
            result.put("name", historicTask.getName());
            result.put("claimTime", historicTask.getClaimTime());
            return result;
        }).collect(toList());
    }
}
