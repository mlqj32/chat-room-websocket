package com.example.websocketdemo3.config;/**
 * @projectName: websockerdemo1
 * @package: com.example.websockerdemo1.config
 * @className: WebSocketConfig
 * @author: mlqj
 * @description:
 */

/**
 *@Author: zdh
 *@CreateTime: 2025-03-15
 *@Description:
 *@Version: 0.1
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

}
