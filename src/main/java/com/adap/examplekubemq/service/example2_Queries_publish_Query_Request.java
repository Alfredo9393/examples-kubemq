/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adap.examplekubemq.service;

import com.adap.examplekubemq.dto.dataDto;
import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import io.kubemq.sdk.commandquery.Request;
import io.kubemq.sdk.commandquery.RequestType;
import io.kubemq.sdk.commandquery.Response;
import io.kubemq.sdk.tools.Converter;
import javax.net.ssl.SSLException;
import org.springframework.stereotype.Service;

/**Publish example 2
 *
 * @author APerez
 */
@Service
public class example2_Queries_publish_Query_Request {
    
    public void Queries_Sending_Query_Request(dataDto data) {
        
        String ChannelName = "example2_channel_Queries", ClientID = "example2_clientId_Queries",
        KubeMQServerAddress = "localhost:50000";
        io.kubemq.sdk.commandquery.ChannelParameters channelParameters = new io.kubemq.sdk.commandquery.ChannelParameters();
        channelParameters.setChannelName(ChannelName);
        channelParameters.setClientID(ClientID);
        channelParameters.setKubeMQAddress(KubeMQServerAddress);
        channelParameters.setRequestType(RequestType.Query);
        channelParameters.setTimeout(5000);
        io.kubemq.sdk.commandquery.Channel channel = new io.kubemq.sdk.commandquery.Channel(channelParameters);
        Request request = new Request();
        
        try {
            request.setBody(Converter.ToByteArray(data));
        } catch (Exception e) {
            System.out.println("error setBody "+e);
        }
        
        Response result;

        try {
            result = channel.SendRequest(request);
            System.out.println("isExecuted: "+result.isExecuted());
            if (!result.isExecuted()) {

                System.out.printf("Response error: %s", result.getError());
                return;
            }
            
            try {
                System.out.println("Response publish:"+Converter.FromByteArray(result.getBody()));
                
            } catch (Exception e) {
                System.out.println("error Response publish "+ e);
            }

//            System.out.printf("Response Received: %s, ExecutedAt: %s", result.getRequestID(), result.getTimestamp());
        } catch (SSLException |  ServerAddressNotSuppliedException e) {
            // TODO Auto-generated catch block
            System.out.println("error SSLException |  ServerAddressNotSuppliedException "+ e);
            e.printStackTrace();
        }

    }
}
