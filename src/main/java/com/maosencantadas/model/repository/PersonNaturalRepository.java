package com.maosencantadas.model.repository;

import com.maosencantadas.model.domain.person.PersonNatural;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonNaturalRepository extends JpaRepository<PersonNatural, Long> {
}