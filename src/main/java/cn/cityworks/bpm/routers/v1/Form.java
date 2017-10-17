package cn.cityworks.bpm.routers.v1;

import cn.cityworks.bpm.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Optional;

/**
 * create by afterloe on 2017/10/17
 */
@RestController
@RequestMapping("/v1/form")
public class Form implements Serializable {

    @Autowired
    private cn.cityworks.bpm.services.Form formService;

    /**
     * 获取流程启动表单
     *
     * @param processId
     * @param processId_Path
     * @return
     */
    @RequestMapping(value = {"/{processId}", "/"}, method = RequestMethod.GET)
    public ResponseDTO getStartFormData(@PathVariable(value = "processId", required = false) String processId
            , @RequestParam(value = "processId", required =false) String processId_Path) {
        Object data = formService.getStartFormData(Optional.ofNullable(processId_Path).orElse(processId));
        return ResponseDTO.build(data);
    }
}
