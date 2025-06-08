package com.maosencantadas.model.service;

import com.maosencantadas.api.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> findAllCustomers();

    CustomerDTO findCustomerById(Long id);

    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO);

    void deleteCustomer(Long id);
}
