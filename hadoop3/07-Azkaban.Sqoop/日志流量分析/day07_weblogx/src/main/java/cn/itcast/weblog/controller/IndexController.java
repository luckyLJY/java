package cn.itcast.weblog.controller;

import cn.itcast.weblog.pojo.AvgReturnPojo;
import cn.itcast.weblog.pojo.FlowReturnPojo;
import cn.itcast.weblog.service.AvgNumService;
import cn.itcast.weblog.service.FlowNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @Autowired
    private AvgNumService avgNumService;

    @RequestMapping("/index.action")
    public String skipToIndex(){
        return "index";
    }
    @RequestMapping("/avgPvNum.action")
    @ResponseBody
    public  AvgReturnPojo getAvgPvNum(){
        AvgReturnPojo returnPojo =  avgNumService.getAvgReturnJson();
        System.out.println("returnPojo:"+returnPojo);
        return returnPojo;
    }







    @Autowired
    private FlowNumService flowNumService;
    @RequestMapping("/flowNum.action")
    @ResponseBody
    public FlowReturnPojo getFlowNum(){
       FlowReturnPojo flowReturnPojo =  flowNumService.getAllFlowNum();
        return flowReturnPojo;
    }
}
