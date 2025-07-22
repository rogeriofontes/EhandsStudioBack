package com.maosencantadas.model.repository;

import com.maosencantadas.model.domain.person.PersonLegal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonLegalRepository extends JpaRepository<PersonLegal, Long> {
}