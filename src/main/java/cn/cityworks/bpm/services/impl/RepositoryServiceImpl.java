package cn.cityworks.bpm.services.impl;

import cn.cityworks.bpm.services.Repository;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
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
public class RepositoryServiceImpl implements Repository {

    @Autowired
    private RepositoryService repositoryService;

    @Override
    public Object listDeveloperProcess(int page, int number) {
        List<ProcessDefinition> data = repositoryService.createProcessDefinitionQuery().list();
        Object processList = data.stream().map(p -> {
            Map m = new LinkedHashMap<>();
            m.put("id", p.getId());
            m.put("name", p.getName());
            m.put("category", p.getCategory());
            m.put("key", p.getKey());
            m.put("description", p.getDescription());
            m.put("version", p.getVersion());
            m.put("resourceName", p.getResourceName());
            m.put("deploymentId", p.getDeploymentId());
            m.put("diagramResourceName", p.getDiagramResourceName());
            m.put("tenantId", p.getTenantId());
            return m;
        }).collect(toList());

        return processList;
    }
}
