package cn.cityworks.bpm.demo.services.impl;

import cn.cityworks.bpm.demo.exceptions.BasicException;
import cn.cityworks.bpm.demo.services.BPMService;
import org.activiti.engine.FormService;
import org.activiti.engine.form.FormProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public Object startProcess(String processId, Map formData, String access_token) {
        List<FormProperty> items = formService.getStartFormData(processId).getFormProperties();
        Map<String, String> variables = new HashMap<>();
        FormProperty lackParameter = items.stream()
                .filter(item -> item.isRequired() && !formData.containsKey(item.getId()))
                .findAny()
                .orElse(null);
        if (null != lackParameter) {
            throw BasicException.build("lack parameter -> " + lackParameter.getId(),
                    HttpStatus.BAD_REQUEST.value());
        }
        items.stream().forEach(item -> {
            String key = item.getId();
            variables.put(key, formData.get(key).toString());
        });
        Object data = formService.submitStartFormData(processId, variables);
        if (null == data) {
            throw BasicException.build("create task failed!");
        }
        return true;
    }
}
