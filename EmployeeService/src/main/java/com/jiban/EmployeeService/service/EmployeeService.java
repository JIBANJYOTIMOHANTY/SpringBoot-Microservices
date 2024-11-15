package com.jiban.EmployeeService.service;

import com.jiban.EmployeeService.entity.Employee;
import com.jiban.EmployeeService.openfeignclient.AddressClient;
import com.jiban.EmployeeService.repository.EmployeeRepository;
import com.jiban.EmployeeService.response.AddressResponse;
import com.jiban.EmployeeService.response.EmployeeResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private WebClient webClient;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private AddressClient addressClient;

    public EmployeeResponse getEmployeeById(int id){
        Employee employee = employeeRepository.findById(id).get();

        //      Automatically map the data
        EmployeeResponse employeeResponse = modelMapper.map(employee, EmployeeResponse.class);
        ResponseEntity<AddressResponse> addressResponseEntity = addressClient.getAddressByEmployeeId(id);
        AddressResponse addressResponse = addressResponseEntity.getBody();
        employeeResponse.setAddressResponse(addressResponse);

        return employeeResponse;
    }

    private AddressResponse callToAddressServiceUsingWebClient(int id){
        return webClient.
                    get().
                    uri("/address/"+id).
                    retrieve().
                    bodyToMono(AddressResponse.class).
                    block();

    }

    public AddressResponse callingAddressServiceUsingRestTemplate(int id){
        ServiceInstance serviceInstance = loadBalancerClient.choose("address-service");

        String uri = serviceInstance.getUri().toString();
        String configPath = serviceInstance.getMetadata().get("configPath");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + uri + configPath + id);

        // return restTemplate.getForObject(uri + configPath"/address/{id}", AddressResponse.class,id);
        // If you want to get the uri and the context path dynamically then you use this return statement and comment the below code and also comment the @LoadBalanced annotation present inside the EmployeeConfig.java present in the config package.(com.jiban.EmployeeService.config)


        return restTemplate.getForObject("http://address-service/address-app/api/address/{id}", AddressResponse.class,id);

    }

}
