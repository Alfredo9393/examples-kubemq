/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adap.examplekubemq.service;

import com.adap.examplekubemq.dto.dataDto;
import com.google.gson.Gson;
import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import io.kubemq.sdk.commandquery.Channel;
import io.kubemq.sdk.commandquery.Responder;
import io.kubemq.sdk.commandquery.Response;
import io.kubemq.sdk.subscription.SubscribeRequest;
import io.kubemq.sdk.subscription.SubscribeType;
import io.kubemq.sdk.tools.Converter;
import java.time.LocalDateTime;
import javax.annotation.PostConstruct;
import javax.net.ssl.SSLException;
import org.springframework.stereotype.Service;

/**Subscribe example 3
 * metodo example3_Queries_subcribe_Query_Requests: Recibe el evento publicado y genera un response
 * @author APerez
 */
@Service
public class example3_Queries_subcribe_Query_Requests {
    
    Channel channel;

    public example3_Queries_subcribe_Query_Requests(Channel channel) {
        this.channel = channel;
        System.out.println("constructor subcriber" +objectToJson(channel));
    }
    
    @PostConstruct
    public  void Queries_Receiving_Query_Requests() {
        System.out.println(" ");

        String ChannelName = "example3_channel_Queries", ClientID = "example3_clientId_Queries",
        KubeMQServerAddress = "localhost:50000";
        Responder.RequestResponseObserver HandleIncomingRequests;
        Responder responder = new Responder(KubeMQServerAddress);
        
        HandleIncomingRequests = request -> {
            System.out.println("********* subcribe ************** ");
            int result=0;
            try {
                System.out.println("get request: "+ objectToJson(Converter.FromByteArray(request.getBody())));
                dataDto data=  (dataDto)Converter.FromByteArray(request.getBody());
                result =suma(data);

            } catch (Exception e) {
                 System.out.println("error request "+e);
            }
         
            Response response = new Response(request);
            response.setCacheHit(false);
            response.setError("None");
            response.setClientID(ClientID);
            try {
                response.setBody(Converter.ToByteArray(result));
            } catch (Exception e) {
                System.out.println("error setBody: "+e);
            }
            response.setExecuted(true);
            response.setMetadata("this is a response");
            response.setTimestamp(LocalDateTime.now());
            return response;
        };
        SubscribeRequest subscribeRequest = new SubscribeRequest();
        subscribeRequest.setChannel(ChannelName);
        subscribeRequest.setClientID(ClientID);
        subscribeRequest.setSubscribeType(SubscribeType.Queries);

        new Thread() {
            public void run() {
                System.out.println("Thread run example3");
                try {
                    System.out.println("SubscribeToRequests");
                    responder.SubscribeToRequests(subscribeRequest, HandleIncomingRequests);
                    System.out.println("sub2");
                } catch (SSLException |  ServerAddressNotSuppliedException e) {
                    // TODO Auto-generated catch block
                    System.out.println("subcribe error: "+e);
                    e.printStackTrace();
                }

            }
        }.start();

    }
    
    public int suma (dataDto data){
        int res =0;
        res=data.getVal1() + data.getVal1();
        return res;
    }
    
    private static String objectToJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
    
    private static String getid() {
        int min = 10;  
        int max = 900;    
        Integer res1 = (int) (Math.random()*(max-min+1)+min);   
        String res = res1.toString();
        return res;
    }


      
}