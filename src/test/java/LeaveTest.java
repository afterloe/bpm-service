import cn.cityworks.bpm.Launch;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricFormProperty;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * create by afterloe on 2017/10/13
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Launch.class)
public class LeaveTest {

    private String currentUserId;
    private String processId;
    private SimpleDateFormat simpleDateFormat;
    private Calendar calendar;

    private static final Logger LOGGER = LoggerFactory.getLogger(LeaveTest.class);

    @Autowired
    private IdentityService identityService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private FormService formService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;

    @Before
    public void initTest() {
        currentUserId = "afterloe.L";
        processId = "myProcess_1";
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        calendar = Calendar.getInstance();
    }

    @Test
    @Deployment(resources = "processes/myProcess_1.bpmn20.xml")
    public void allApproved() throws Exception {
        identityService.setAuthenticatedUserId(currentUserId); // 设置发起请假的用户
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(processId).singleResult(); // 启动流程

        Map<String, String> variables = new HashMap<>(); // 初始化表单数据

        /*
            模拟填写表单
         */
        String startDate = simpleDateFormat.format(calendar.getTime()); // 填写请假开始时间
        calendar.add(Calendar.DAY_OF_MONTH, 2); // 填写请假时间为2个月
        String endDate = simpleDateFormat.format(calendar.getTime());
        variables.put("startDate", startDate);
        variables.put("endDate", endDate);
        variables.put("reason", "年假调休加公共休息加产假");

        ProcessInstance processInstance = formService
                .submitStartFormData(processDefinition.getId(), variables); //使用表单启动流程
        assertNotNull(processDefinition);

        /*
           部门领导审批
         */
        Task deptLeaderTask = taskService.createTaskQuery().taskCandidateGroup("deptLeader").singleResult();
        assertNotNull(deptLeaderTask);
        variables.clear();
        variables.put("deptLeaderApprove", "true");
        formService.submitTaskFormData(deptLeaderTask.getId(), variables);

        /*
           人事审批
         */
        Task hrTask = taskService.createTaskQuery().taskCandidateGroup("hr").singleResult();
        assertNotNull(hrTask);
        variables.clear();
        variables.put("hrApprove", "true");
        formService.submitTaskFormData(hrTask.getId(), variables);

        /*
           销假
         */
        Task reportBackTask = taskService.createTaskQuery().taskAssignee(currentUserId).singleResult();
        assertNotNull(reportBackTask);
        variables.clear();
        variables.put("reportBackDate", endDate);
        variables.put("result", "ok");
        formService.submitTaskFormData(reportBackTask.getId(), variables);

        /*
            验证流程是否结束
         */
        HistoricProcessInstance historicProcessInstance = historyService
                .createHistoricProcessInstanceQuery().finished().singleResult();
        assertNotNull(historicProcessInstance);
        Map<String, Object> historyVariables = packageVariables(processInstance);
        assertEquals("ok", historyVariables.get("result"));
    }

    /**
     * 读取流程中的数据信息
     *
     * @param processInstance
     * @return
     */
    private Map<String,Object> packageVariables(ProcessInstance processInstance) {
        Map<String, Object> variables = new HashMap<>();
        List<HistoricDetail> list = historyService.createHistoricDetailQuery()
                .processInstanceId(processInstance.getId()).list();
        list.stream().forEach(h -> {
            if (h instanceof HistoricFormProperty) {
                HistoricFormProperty field = (HistoricFormProperty) h;
                variables.put(field.getPropertyId(), field.getPropertyValue());
            } else if (h instanceof HistoricVariableUpdate) {
                HistoricVariableUpdate variable = (HistoricVariableUpdate) h;
                variables.put(variable.getVariableName(), variable.getVariableName());
            }
        });
        LOGGER.info("LeaveTest.packageVariables -> {}", variables);
        return variables;
    }

}
