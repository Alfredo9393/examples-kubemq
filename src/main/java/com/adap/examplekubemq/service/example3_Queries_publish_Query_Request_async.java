/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adap.examplekubemq.service;

import com.adap.examplekubemq.dto.dataDto;
import com.google.gson.Gson;
import io.grpc.stub.StreamObserver;
import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import io.kubemq.sdk.commandquery.Channel;
import io.kubemq.sdk.commandquery.Request;
import io.kubemq.sdk.commandquery.Response;
import io.kubemq.sdk.tools.Converter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLException;
import org.springframework.stereotype.Service;

/**Publish example 3
 * Metodo Queries_Sending_Query_Request_async publica el evento 
 * @author APerez
 */
@Service
public class example3_Queries_publish_Query_Request_async {

    Channel channel;

    public example3_Queries_publish_Query_Request_async(Channel channel) {
        this.channel = channel;
        System.out.println("constructor publish" +objectToJson(channel));
    }
    
    public void Queries_Sending_Query_Request_async(dataDto data) {
        System.out.println(" ");
        System.out.println("********* publish ************** ");

        Request request = new Request();
        try {
            request.setBody(Converter.ToByteArray(data));
        } catch (IOException ex) {
            System.out.println("error: setBody "+ex);
        }
        
        StreamObserver<Response> response = new StreamObserver<Response>() {

            @Override
            public void onNext(Response value) {
                System.out.println("isExecuted subcriber: "+value.isExecuted());

                if (!value.isExecuted()) {
                    System.out.printf("Response error: %s", value.getError());
                } else {
                    try {
                        System.out.println("Response publish:"+Converter.FromByteArray(value.getBody()));
//                        System.out.printf("Response Received: %s, ExecutedAt %s", value.getRequestID(),value.getTimestamp());
                    } catch (IOException ex) {
                        System.out.println("error1: "+ex);
                    } catch (ClassNotFoundException ex) {
                        System.out.println("error2: "+ex);
                    }
                }

            }

            @Override
            public void onError(Throwable t) {
                System.out.printf("onError: %s", t.getMessage());
            }

            @Override
            public void onCompleted() {

            }
        };
        try {
            System.out.println("request ByteArray: "+objectToJson(request));    
            System.out.println("request objt: "+objectToJson(data));            
            channel.SendRequestAsync(request, response);
        
        } catch (ServerAddressNotSuppliedException ex) {
            System.out.println("error ServerAddressNotSuppliedException: "+ex);
        } catch (SSLException ex) {
            System.out.println("error SSLException: "+ex);
        }
    }
    
    private static String objectToJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }
}
