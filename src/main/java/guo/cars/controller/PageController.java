package guo.cars.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description:
 * @author: guoyiming
 **/
@Controller
public class PageController {

    @RequestMapping("/home")
    public String home(){
        return "index";
    }

    @RequestMapping("/upload")
    public String upload(){
        return "upload";
    }

    @RequestMapping("/infos")
    public String infos(){
        return "infos";
    }

    @RequestMapping("/show")
    public String show(){ return "show"; }

    @RequestMapping("/test_result")
    public String testResult(){
        return "test_result";
    }


}
