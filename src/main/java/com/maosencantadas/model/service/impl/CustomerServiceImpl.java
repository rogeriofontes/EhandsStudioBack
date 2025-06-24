package com.maosencantadas.model.service.impl;

import com.maosencantadas.exception.ResourceNotFoundException;
import com.maosencantadas.model.domain.customer.Customer;
import com.maosencantadas.model.domain.user.User;
import com.maosencantadas.model.domain.user.UserRole;
import com.maosencantadas.model.repository.CustomerRepository;
import com.maosencantadas.model.service.CustomerService;
import com.maosencantadas.model.service.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final IUserService userService;

    @Override
    @EntityGraph(attributePaths = {"user"})
    public List<Customer> findAll() {
        log.info("Listing all customers");
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(Long id) {
        log.info("Finding customer by id: {}", id);
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));
    }

    @Override
    @Transactional
    public Customer save(Customer customer) {
        log.info("Saving new customer: {}", customer.getName());

        if (customer.getUser() == null || customer.getUser().getId() == null) {
            throw new IllegalArgumentException("Customer must have a user with a valid ID");
        }

        Long userId = customer.getUser().getId();
        Optional<User> userById = userService.findById(userId);
        if (userById.isPresent()) {
            log.debug("User with ID {} already exists, using existing user", userId);

            if (!userById.get().getRole().equals(UserRole.CUSTOMER)) {
                throw new IllegalArgumentException("The user must have CUSTOMER role");
            }

            customer.setUser(userById.get());
            return customerRepository.save(customer);
        }

        return null;
    }


    @Override
    public Customer update(Long id, Customer customer) {
        log.info("Updating customer with id: {}", id);
        return customerRepository.findById(id)
                .map(existingCustomer -> {
                    existingCustomer.setName(customer.getName());
                    existingCustomer.setEmail(customer.getEmail());
                    existingCustomer.setPhone(customer.getPhone());
                    existingCustomer.setAddress(customer.getAddress());
                    existingCustomer.setUser(customer.getUser());
                    existingCustomer.setCpf(customer.getCpf());
                    existingCustomer.setWhatsapp(customer.getWhatsapp());
                    existingCustomer.setMedia(customer.getMedia());
                    return customerRepository.save(existingCustomer);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id " + id));

    }

    @Override
    public void delete(Long id) {
        log.info("Deleting customer with id: {}", id);
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found with id " + id);
        }
        customerRepository.deleteById(id);
    }
}
