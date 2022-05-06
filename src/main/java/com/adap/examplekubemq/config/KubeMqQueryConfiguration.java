/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.adap.examplekubemq.config;

import io.kubemq.sdk.commandquery.Channel;
import io.kubemq.sdk.commandquery.RequestType;
import io.kubemq.sdk.event.Subscriber;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author APerez
 */
@Configuration
@ConfigurationProperties("kubemq")
public class KubeMqQueryConfiguration {
    private String address;

    @Bean
    public Subscriber subscriber() {
        return new Subscriber(address);
    }

    @Bean
    public Channel channel() {
        String ChannelName = "example3_channel_Queries", 
        ClientID = "example3_clientId_Queries",
        KubeMQServerAddress = "localhost:50000";
        io.kubemq.sdk.commandquery.ChannelParameters channelParameters = new io.kubemq.sdk.commandquery.ChannelParameters();
        channelParameters.setChannelName(ChannelName);
        channelParameters.setClientID(ClientID);
        channelParameters.setKubeMQAddress(KubeMQServerAddress);
        channelParameters.setRequestType(RequestType.Query);
        channelParameters.setTimeout(10000); //100000
        return new Channel(channelParameters);
    }

    String getAddress() {
        return address;
    }

    void setAddress(String address) {
        this.address = address;
    }
}
