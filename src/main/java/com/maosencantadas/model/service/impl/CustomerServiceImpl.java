package com.maosencantadas.model.service.impl;

import com.maosencantadas.api.dto.CustomerDTO;
import com.maosencantadas.api.mapper.CustomerMapper;
import com.maosencantadas.exception.ResourceNotFoundException;
import com.maosencantadas.model.domain.customer.Customer;
import com.maosencantadas.model.repository.CustomerRepository;
import com.maosencantadas.model.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> findAllCustomers() {
        log.info("Listing all customers");
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customerMapper::toDTO)
                .toList();
    }

    @Override
    public CustomerDTO findCustomerById(Long id) {
        log.info("Finding for customer by id: {}", id);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));
        return customerMapper.toDTO(customer);
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving new customer: {}", customerDTO.getName());
        Customer customer = customerMapper.toEntity(customerDTO);
        Customer save = customerRepository.save(customer);
        return customerMapper.toDTO(save);
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        log.info("Updating customer with id: {}", id);
        Customer customerUpdated = customerRepository.findById(id)
                .map(customer -> {
                    customer.setName(customerDTO.getName());
                    customer.setAddress(customerDTO.getAddress());
                    customer.setPhone(customerDTO.getPhone());
                    customer.setEmail(customerDTO.getEmail());
                    return customerRepository.save(customer);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));
        return customerMapper.toDTO(customerUpdated);
    }

    @Override
    public void deleteCustomer(Long id) {
        log.info("Deleting customer with id: {}", id);
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found with id  " + id);
        }
        customerRepository.deleteById(id);
    }
}
