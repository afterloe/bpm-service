package cn.cityworks.bpm.services;

import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.FormType;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * create by afterloe on 2017/10/17
 */
public interface Form extends Serializable {

    /**
     * 获取流程启动表单
     *
     * @param processId
     * @return
     */
    Object getStartFormData(String processId);

    /**
     * 处理FormProperty 数据 [能够写成JSON发送]
     */
    Function<FormProperty, Map> handlerFormProperty = item -> {
        Map value = new LinkedHashMap();
        value.put("id", item.getId());
        value.put("name", item.getName());
        FormType type = item.getType();
        Map typeInfo = new LinkedHashMap();
        typeInfo.put("name", type.getName());
        if (type.getName().equals("enum")) {
            typeInfo.put("values", type.getInformation("values"));
        }
        value.put("type", typeInfo);
        value.put("value", item.getValue());
        value.put("required", item.isRequired());
        value.put("readable", item.isReadable());
        value.put("writable", item.isWritable());
        return value;
    };
}
