package com.micro.discovery;

import com.netflix.client.ClientFactory;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.*;
import feign.ribbon.LBClient;
import feign.ribbon.LBClientFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.ribbon.StaticServerList;

import java.util.List;

public class RibbonLBClientFactory implements LBClientFactory {

  DiscoveryClient discoveryClient;
  public RibbonLBClientFactory(DiscoveryClient discoveryClient){
    this.discoveryClient=discoveryClient;
  }

  @Override
  public LBClient create(String s) {

    List<ServiceInstance> instances = discoveryClient.getInstances(s);
    IClientConfig iClientConfig= ClientFactory.getNamedConfig(s);
    IRule iRule= new RoundRobinRule();
    IPing iPing= new NoOpPing();
    Server[] serverArray = new Server[instances.size()];
    int index=0;
    for (ServiceInstance serverInstance:instances){
      serverArray[index++]=new Server(serverInstance.getHost(),serverInstance.getPort());
    }
    StaticServerList serverList= new StaticServerList(serverArray);
    DynamicServerListLoadBalancer dynamicServerListLoadBalancer = new DynamicServerListLoadBalancer(iClientConfig, iRule, iPing, serverList, null);

    LBClient lbClient= LBClient.create(dynamicServerListLoadBalancer,iClientConfig);
    return  lbClient;
  }
}


