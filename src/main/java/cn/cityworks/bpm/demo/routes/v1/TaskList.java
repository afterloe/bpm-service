package cn.cityworks.bpm.demo.routes.v1;

import cn.cityworks.bpm.demo.domain.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

/**
 * create by afterloe on 2017/10/13
 */
@RestController
@RequestMapping("/v1/task")
public class TaskList implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskList.class);

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseDTO listTask(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                @RequestParam(value = "number", required = false, defaultValue = "50") int number) {
        Object data = null;

        return ResponseDTO.build(data);
    }
}
