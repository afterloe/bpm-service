package cn.cityworks.bpm.services.impl;

import cn.cityworks.bpm.exceptions.BasicException;
import cn.cityworks.bpm.integrate.UserClient;
import cn.cityworks.bpm.services.Form;
import cn.cityworks.bpm.services.Runtime;
import cn.cityworks.bpm.services.Task;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
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
public class TaskServiceImpl implements Task {

    @Autowired
    private TaskService taskService;
    @Autowired
    private UserClient userClient;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private Form form;
    @Autowired
    private Runtime runtime;

    /**
     * 获取任务 公用方法
     *
     * @param taskId
     * @return
     */
    private org.activiti.engine.task.Task getTask(String taskId) {
        org.activiti.engine.task.Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (null == task) {
            throw BasicException.build("no such this task! -> " + taskId, HttpStatus.SC_NOT_FOUND);
        }
        return  task;
    }

    private Map printTask(Object task) {
        if (task instanceof org.activiti.engine.task.Task) {
            return Task.toString.apply((org.activiti.engine.task.Task)task);
        }

        return printHistoryTask(task);
    }

    private Map printHistoryTask(Object task) {
        if (task instanceof HistoricTaskInstance ) {
            HistoricTaskInstance taskInstance = (HistoricTaskInstance) task;
            Map result = new LinkedHashMap();
            result.put("type", "historicTask");
            result.put("name", taskInstance.getName());
            result.put("dueDate", taskInstance.getDueDate());
            result.put("localVariables", taskInstance.getTaskLocalVariables());
            result.put("processVariables",taskInstance.getProcessVariables());
            result.put("assignee", taskInstance.getAssignee());
            result.put("owner", taskInstance.getOwner());
            result.put("description", taskInstance.getDescription());
            result.put("createTime", taskInstance.getCreateTime());
            result.put("workTime", taskInstance.getWorkTimeInMillis());
            result.put("deleteReason", taskInstance.getDeleteReason());
            return result;
        }
        throw BasicException.build("this type of task is not support.");
    }

    @Override
    public Object listByProcess(String processId) {
        List taskList = taskService.createTaskQuery().processInstanceId(processId).list();
        if (0 == taskList.size()) {
            taskList = historyService.createHistoricTaskInstanceQuery().processInstanceId(processId).list();
        }
        return taskList.stream().map(task -> printTask(task)).collect(toList());
    }

    @Override
    public Object listByBusinessKey(String businessKey) {
        List taskList = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).list();
        if (0 == taskList.size()) {
            taskList = historyService.createHistoricTaskInstanceQuery().processInstanceBusinessKey(businessKey).list();
        }
        return taskList.stream().map(task -> printTask(task)).collect(toList());
    }

    @Override
    public Object getTask(String taskId, String uid) {
        org.activiti.engine.task.Task task = getTask(taskId);
        Map result = toString.apply(task);
        String formKey = task.getFormKey();
        if (null == formKey) {
            result.put("formProperties", form.getTaskFormData(taskId));
        } else {
            result.put("formKey", formKey);
        }
        return result;
    }

    @Override
    public Object claimTask(String taskId, String uid) {
        org.activiti.engine.task.Task task = getTask(taskId);
        String assignee = task.getAssignee();
        if (null == assignee) {
            taskService.claim(taskId, uid);
            return true;
        }
        if (!assignee.equals(uid)) {
            throw BasicException.build("task has been assigned");
        }

        return true;
    }

    @Override
    public Object completeTask(String taskId, Map variables) {
        checkedParameter(variables, "uid");
        String uid = variables.remove("uid").toString();
        org.activiti.engine.task.Task task = getTask(taskId);
        if (!uid.equals(task.getAssignee())) {
            throw BasicException.build("this task has not been assigned to you. please sign this task ..."
                    , HttpStatus.SC_BAD_REQUEST);
        }
        taskService.complete(taskId, variables);
        task = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).active().singleResult();
        return null == task ? true: toString.apply(task);
    }

    @Override
    public Object listTaskByUserId(String userId, int page, int number) {
        return taskService.createTaskQuery().taskAssigneeLike(userId).list().stream()
                .map(task -> {
                    Map repo = new LinkedHashMap();
                    repo.put("name", task.getName());
                    repo.put("id", task.getId());
                    repo.put("processInstanceId", task.getProcessInstanceId());
                    repo.put("assignee", task.getAssignee());
                    repo.put("createTime", task.getCreateTime());
                    repo.put("description", task.getDescription());
                    repo.put("owner", task.getOwner());
                    return repo;
                }).collect(toList());
    }

    @Override
    public Object countByCanSignTask(String userId) {
        Map data = (Map) checkedResponseMap.apply(userClient.listGroupsByUserId(userId, 0, 200));
        List<Map> groups = (List) data.get("content");

        // 获取 所在组内的所有任务列表
        long taskNumber = groups.stream()
                .map(group -> taskService.createTaskQuery()
                        .taskCandidateGroup(group.get("groupName").toString()).count())
                .reduce((d1, d2) -> d1 + d2).orElse(0l);

        return taskNumber;
    }

    @Override
    public Object listCanSignTaskByGroup(String groupKey, int page, int number) {
        List<org.activiti.engine.task.Task> list = taskService.createTaskQuery()
                .taskCandidateGroup(groupKey.toString()).listPage(page * number, number);

        long size = taskService.createTaskQuery().taskCandidateGroup(groupKey.toString()).count();
        long totalPages = size % number + 1;

        Map response = new LinkedHashMap();

        response.put("totalElements", size);
        response.put("last", page >= totalPages);
        response.put("totalPages", totalPages);
        response.put("size", number);
        response.put("number", page);
        response.put("numberOfElements", number);
        response.put("sort", null);
        response.put("first", page <= 1);
        response.put("content", list.stream().map(task -> {
            Map repo = new LinkedHashMap();
            repo.put("name", task.getName());
            repo.put("id", task.getId());
            repo.put("processInstanceId", task.getProcessInstanceId());
            repo.put("assignee", task.getAssignee());
            repo.put("createTime", task.getCreateTime());
            repo.put("description", task.getDescription());
            repo.put("owner", task.getOwner());

            return repo;
        }).collect(toList()));

        return response;
    }

}
