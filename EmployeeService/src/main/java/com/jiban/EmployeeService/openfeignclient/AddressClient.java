package com.jiban.EmployeeService.openfeignclient;

import com.jiban.EmployeeService.response.AddressResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ADDRESS-SERVICE", path = "/address-app/api/")
public interface AddressClient {
    @GetMapping("/address/{employeeId}")
    public ResponseEntity<AddressResponse> getAddressByEmployeeId(@PathVariable("employeeId") int id);
}
