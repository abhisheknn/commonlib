package com.micro.discovery;

import feign.Feign;
import feign.Logger;
import feign.Request;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.ribbon.RibbonClient;
import org.springframework.cloud.client.discovery.DiscoveryClient;

public class FeignClientProvider {

  public Object getServiceClient(DiscoveryClient discoveryClient, Class apiType, String serviceName) {
    //final Object tokenManagerServiceClient;
    RibbonLBClientFactory tokenManagerRibbonClient=new RibbonLBClientFactory(discoveryClient);
    tokenManagerRibbonClient.create(serviceName);
    RibbonClient ribbonClient=RibbonClient.builder().lbClientFactory(tokenManagerRibbonClient).build();
    return Feign.builder()
      .client(ribbonClient)
      .decoder(new JacksonDecoder())
      .encoder(new JacksonEncoder())
      .logLevel(Logger.Level.FULL)
      .options(new Request.Options(30000, 30000))
      .target(apiType,"http://"+serviceName);

  }

}
