package cn.cityworks.bpm.services.impl;

import cn.cityworks.bpm.config.Dictionaries;
import cn.cityworks.bpm.exceptions.BasicException;
import cn.cityworks.bpm.services.Form;
import org.activiti.engine.FormService;
import org.activiti.engine.form.FormData;
import org.activiti.engine.form.StartFormData;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * create by afterloe on 2017/10/17
 */
public class FormServiceImpl implements Form {

    @Autowired
    private FormService formService;

    @Override
    public Object getStartFormData(String processId) {
        StartFormData startFormData = formService.getStartFormData(processId);
        if (null == startFormData) {
            throw BasicException.build("no such this process! by -> " + processId, HttpStatus.SC_NOT_FOUND);
        }
        Map result = new LinkedHashMap<>();
        String formKey = startFormData.getFormKey();
        if (null != formKey) {
            result.put("type", Dictionaries.FORM_DATA_TYPE_KEY);
            result.put("formData", formKey);
        } else {
            result.put("type", Dictionaries.FORM_DATA_TYPE_PROPERTY);
            result.put("formData", formService.getStartFormData(processId).getFormProperties().stream()
                    .map(handlerFormProperty).collect(toList()));
        }
        return result;
    }

    @Override
    public Object getTaskFormData(String taskId) {
        FormData formData = formService.getTaskFormData(taskId);
        return formData.getFormProperties().stream().map(handlerFormProperty).collect(toList());
    }
}
