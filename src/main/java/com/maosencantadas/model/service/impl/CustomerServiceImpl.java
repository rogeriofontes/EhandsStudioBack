package com.maosencantadas.model.service.impl;

import com.maosencantadas.api.dto.CustomerDTO;
import com.maosencantadas.api.dto.UserDTO;
import com.maosencantadas.api.mapper.CustomerMapper;
import com.maosencantadas.exception.ResourceNotFoundException;
import com.maosencantadas.model.domain.customer.Customer;
import com.maosencantadas.model.domain.user.User;
import com.maosencantadas.model.domain.user.UserRole;
import com.maosencantadas.model.repository.CustomerRepository;
import com.maosencantadas.model.repository.UserRepository;
import com.maosencantadas.model.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @EntityGraph(attributePaths = {"user"})
    public List<CustomerDTO> findAllCustomers() {
        log.info("Listing all customers");
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customerMapper::toDTO)
                .toList();
    }

    @Override
    public CustomerDTO findCustomerById(Long id) {
        log.info("Finding customer by id: {}", id);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));
        return customerMapper.toDTO(customer);
    }

    @Override
    @Transactional
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving new customer: {}", customerDTO.getName());

        Optional<User> userById = userRepository.findById(customerDTO.getUserId());
        if (userById.isPresent()) {
            log.debug("User with ID {} already exists, using existing user", customerDTO.getUserId());

            //if (userById.get().getRole() != UserRole.CUSTOMER) {
              //  throw new IllegalArgumentException("The user must have CUSTOMER role");
            //}

            Customer customer = customerMapper.toEntity(customerDTO);
            customer.setUser(userById.get());

            Customer savedCustomer = customerRepository.save(customer);
            log.debug("Customer saved with ID: {}", savedCustomer.getId());

            return customerMapper.toDTO(savedCustomer);
        }

        return null;
    }


    private User createUserFromDTO(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setRole(UserRole.CUSTOMER);

        return userRepository.save(user);
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        log.info("Updating customer with id: {}", id);

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));

        User user = userRepository.findById(customerDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + customerDTO.getUserId()));

        if (user.getRole() != UserRole.CUSTOMER) {
            throw new IllegalArgumentException("The user must have CUSTOMER role");
        }

        customer.setUser(user);
        customer.setName(customerDTO.getName());
        customer.setAddress(customerDTO.getAddress());
        customer.setPhone(customerDTO.getPhone());
        customer.setWhatsapp(customerDTO.getWhatsapp());
        customer.setCpf(customerDTO.getCpf());
        customer.setEmail(customerDTO.getEmail());

        Customer updated = customerRepository.save(customer);
        return customerMapper.toDTO(updated);
    }

    @Override
    public void deleteCustomer(Long id) {
        log.info("Deleting customer with id: {}", id);
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found with id " + id);
        }
        customerRepository.deleteById(id);
    }
}
