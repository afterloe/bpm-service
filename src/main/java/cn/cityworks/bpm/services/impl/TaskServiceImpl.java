package cn.cityworks.bpm.services.impl;

import cn.cityworks.bpm.integrate.UserClient;
import cn.cityworks.bpm.services.Task;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public Object claimTask(String taskId, String uid) {
        // TODO
        return null;
    }

    @Override
    public Object promoteProcess(String processId, String uid) {
        org.activiti.engine.task.Task task = taskService.createTaskQuery()
                .processInstanceId(processId).singleResult();

        return null;
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
