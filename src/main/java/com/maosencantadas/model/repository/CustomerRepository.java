package com.maosencantadas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.maosencantadas.model.domain.customer.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}