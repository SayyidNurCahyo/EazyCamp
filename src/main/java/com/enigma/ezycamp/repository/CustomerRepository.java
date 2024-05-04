package com.enigma.ezycamp.repository;

import com.enigma.ezycamp.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    @Query("select c.* from m_customer as c join m_user_account as ua where c.id = :id and ua.is_enable = true")
    Optional<Customer> findByIdCustomer(@Param("id") String id);
}
