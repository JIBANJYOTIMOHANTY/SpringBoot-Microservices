package com.jiban.Address.service;

import com.jiban.Address.entity.Address;
import com.jiban.Address.repository.AddressRepository;
import com.jiban.Address.response.AddressResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ModelMapper modelMapper;

    public AddressResponse findAddressByEmployeeId(int employeeId){
        Address address = addressRepository.findAddressByEmployeeId(employeeId);
        AddressResponse addressResponse = modelMapper.map(address, AddressResponse.class);
        return addressResponse;
    }
}
