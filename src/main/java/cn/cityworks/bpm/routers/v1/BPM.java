package cn.cityworks.bpm.routers.v1;

import cn.cityworks.bpm.domain.ResponseDTO;
import cn.cityworks.bpm.services.BPMService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * create by afterloe on 2017/10/15
 */
@RestController
@RequestMapping("/v1/bpm")
public class BPM implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(BPM.class);

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private BPMService bpmService;

    /**
     * 获取所有活动流程
     *
     * @param page
     * @param number
     * @return
     */
    @RequestMapping(value = "list/active", method = RequestMethod.GET)
    public ResponseDTO listActiveProcess(@RequestParam(value = "page", required = false, defaultValue = "0") int page
            , @RequestParam(value = "number", required = false, defaultValue = "50") int number) {
        List<ProcessInstance> data = runtimeService.createProcessInstanceQuery().active().list();
        Object processList = data.stream().map(p -> {
            Map m = new LinkedHashMap<>();
            m.put("id", p.getProcessDefinitionId());
            m.put("processDefinitionName", p.getProcessDefinitionName());
            m.put("processInstanceId", p.getProcessInstanceId());
            m.put("name", p.getName());
            return m;
        }).collect(toList());

        return ResponseDTO.build(processList);
    }

    /**
     * 获取已经部署的流程列表
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseDTO listDeveloperProcess(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page
            , @RequestParam(value = "number", required = false, defaultValue = "50") int number) {
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
        return ResponseDTO.build(processList);
    }
}
