package com.maosencantadas.model.service;

import com.maosencantadas.model.domain.person.PersonLegal;

import java.util.List;

public interface PersonLegalService {

    List<PersonLegal> findAll();

    PersonLegal findById(Long id);

    PersonLegal save(PersonLegal personLegal);

    PersonLegal update(Long id, PersonLegal personLegal);

    void delete(Long id);
}
