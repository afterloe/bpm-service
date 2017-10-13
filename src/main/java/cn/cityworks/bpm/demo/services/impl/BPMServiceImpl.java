package cn.cityworks.bpm.demo.services.impl;

import cn.cityworks.bpm.demo.services.BPMService;
import org.activiti.engine.FormService;
import org.activiti.engine.form.FormProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * create by afterloe on 2017/10/13
 */
@Service
public class BPMServiceImpl implements BPMService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BPMServiceImpl.class);

    @Autowired
    private FormService formService;

    @Override
    public Object getProcessFormDataByStart(String processId) {
        return formService.getStartFormData(processId).getFormProperties();
    }

    @Override
    public Object startProcess(String processId, Map variables) {
        List<FormProperty> items = formService.getStartFormData(processId).getFormProperties();
        
        return null;
    }
}
