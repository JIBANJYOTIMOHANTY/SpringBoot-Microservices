package com.jiban.Address.repository;

import com.jiban.Address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    @Query(nativeQuery = true, value = "SELECT ea.id, ea.lane1, ea.lane2, ea.state, ea.zip FROM employee_service.address ea\n" +
            "join \n" +
            "employee_service.employee e \n" +
            "on e.id = ea.employeeId\n" +
            "where ea.employeeId = :employeeId"
    )
    Address findAddressByEmployeeId(@Param("employeeId") int employeeId);
}
