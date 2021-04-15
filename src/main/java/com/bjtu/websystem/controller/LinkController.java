package com.bjtu.websystem.controller;

import com.bjtu.websystem.model.datasetModels.Link;
import com.bjtu.websystem.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
public class LinkController {

    /***
     * code 请求响应代码
     */
    private static final int SUCCESS = 0;
    private static final int FAILURE = 1;
    private static final int EXCEPTION = 2;

    @Autowired
    LinkService linkService;

    @RequestMapping("/getAllLinks")
    @ResponseBody
    public Map<String, Object> getAllLinks(){

        Map<String, Object> resMap = new HashMap<String, Object>(2);

        List<Link> linkList = linkService.getAllLinks();
        if (linkList == null){
            resMap.put("data", null);
            resMap.put("code", FAILURE);

        }else {
            resMap.put("data", linkList);
            resMap.put("code", SUCCESS);
        }
        return resMap;

    }

    @PostMapping("/editLinkById")
    @ResponseBody
    public Map<String, Object> editLinkById(@RequestBody Link[] links){

        Map<String, Object> resMap = new HashMap<String, Object>(1);

        int result1 = linkService.editLinkById(links[0]);
        int result2 = linkService.editLinkById(links[1]);
        int result = result1 + result2;
        if (result == 1 || result == 2){

            resMap.put("code", SUCCESS);

        }else {
            resMap.put("code", FAILURE);
        }
        return resMap;

    }

}
