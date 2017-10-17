package cn.cityworks.bpm.services.impl;

import cn.cityworks.bpm.services.Task;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * create by afterloe on 2017/10/17
 */
public class TaskServiceImpl implements Task {

    @Autowired
    private TaskService taskService;

    @Override
    public Object listTaskByUserId(String userId, int page, int number) {
        return taskService.createTaskQuery()
                .taskAssigneeLike(userId).list().stream()
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

}
