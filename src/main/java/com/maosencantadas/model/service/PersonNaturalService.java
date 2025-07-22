package com.maosencantadas.model.service;

import com.maosencantadas.model.domain.person.PersonNatural;

import java.util.List;

public interface PersonNaturalService {

    List<PersonNatural> findAll();

    PersonNatural findById(Long id);

    PersonNatural save(PersonNatural personNatural);

    PersonNatural update(Long id, PersonNatural personNatural);

    void delete(Long id);
}
