package com.jiban.EmployeeService.openfeignclient;

import feign.Feign;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;

@LoadBalancerClient(name = "ADDRESS-SERVICE", configuration = MyCustromLoadBalancerConfiguration.class)
public class AddressServiceLoadBalancer {
    public Feign.Builder feignBuilder(){
        return Feign.builder();
    }
}
