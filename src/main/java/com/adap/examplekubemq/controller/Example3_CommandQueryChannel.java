/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adap.examplekubemq.controller;

import com.adap.examplekubemq.dto.dataDto;
import com.adap.examplekubemq.service.example3_Queries_publish_Query_Request_async;
import com.adap.examplekubemq.service.example3_Queries_subcribe_Query_Requests;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * Ejemplo de comunicacion sincrona kubMQ
 * 1- se ejecuta el recurso 'http://localhost:8050/kubemq/example3_commandQueryChannel' con 2 numeros de entrada  
 * 2- se publica y genera una respuesta
 * @author APerez
 */
@RestController
@RequestMapping("/kubemq")
public class Example3_CommandQueryChannel {        
    @Autowired
    private example3_Queries_subcribe_Query_Requests subcribe;
    
    @Autowired
    private example3_Queries_publish_Query_Request_async publish;
    
    
    @PostMapping(value="/example3_commandQueryChannel")
    public void CommandQueryChannel(@RequestBody dataDto data) throws IOException{
        publish.Queries_Sending_Query_Request_async(data);
    }
    
    //Este recurso se ocupo ejecutar el response de forma manual 
    @GetMapping(value="/example3_commandQueryChannel")
    public void CommandQueryChannel() throws IOException{
      subcribe.Queries_Receiving_Query_Requests();
    }
}
