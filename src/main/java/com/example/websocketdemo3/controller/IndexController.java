package com.example.websocketdemo3.controller;/**
 * @projectName: websockerdemo1
 * @package: com.example.websockerdemo1.controller
 * @className: IndexController
 * @author: mlqj
 * @description:
 */

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *@Author: zdh
 *@CreateTime: 2025-03-15
 *@Description:
 *@Version: 0.1
 */
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index(){
        return "socket";
    }

}
