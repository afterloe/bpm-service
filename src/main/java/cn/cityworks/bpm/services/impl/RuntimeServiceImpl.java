package cn.cityworks.bpm.services.impl;

import cn.cityworks.bpm.services.Runtime;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
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
public class RuntimeServiceImpl implements Runtime {

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public Object listActive(int page, int number) {
        List<ProcessInstance> data = runtimeService.createProcessInstanceQuery().active().list();
        Object processList = data.stream().map(p -> {
            Map m = new LinkedHashMap<>();
            m.put("id", p.getProcessDefinitionId());
            m.put("processDefinitionName", p.getProcessDefinitionName());
            m.put("processInstanceId", p.getProcessInstanceId());
            m.put("name", p.getName());
            return m;
        }).collect(toList());

        return data;
    }
}
