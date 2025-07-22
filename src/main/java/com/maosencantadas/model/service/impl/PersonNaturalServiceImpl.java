package com.maosencantadas.model.service.impl;

import com.maosencantadas.exception.ResourceNotFoundException;
import com.maosencantadas.model.domain.person.PersonNatural;
import com.maosencantadas.model.repository.PersonNaturalRepository;
import com.maosencantadas.model.service.ArtistService;
import com.maosencantadas.model.service.PersonNaturalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonNaturalServiceImpl implements PersonNaturalService {

    private final PersonNaturalRepository personNaturalRepository;
    private final ArtistService artistService;

    @Override
    public List<PersonNatural> findAll() {
        log.info("Listing all personNaturals with artist and category");
        return personNaturalRepository.findAll();
    }

    @Override
    public PersonNatural findById(Long id) {
        log.info("Finding personNatural by id: {}", id);
        return personNaturalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PersonNatural not found with id " + id));
    }

    @Override
    public PersonNatural save(PersonNatural personNatural) {
        log.info("Saving new personNatural");
        return personNaturalRepository.save(personNatural);
    }

    @Override
    public PersonNatural update(Long id, PersonNatural personNatural) {
        log.info("Updating personNatural with id: {}", id);
        return personNaturalRepository.findById(id)
                .map(existingPersonNatural -> {
                    existingPersonNatural.setName(personNatural.getName());
                    existingPersonNatural.setEmail(personNatural.getEmail());
                    existingPersonNatural.setPhone(personNatural.getPhone());
                    existingPersonNatural.setWhatsapp(personNatural.getWhatsapp());
                    existingPersonNatural.setAddress(personNatural.getAddress());
                    existingPersonNatural.setAddress(personNatural.getAddress());
                    existingPersonNatural.setDocumentNumber(personNatural.getDocumentNumber());
                    existingPersonNatural.setIdentityNumber(personNatural.getIdentityNumber());
                    existingPersonNatural.setBirthDate(personNatural.getBirthDate());
                    return personNaturalRepository.save(existingPersonNatural);
                })
                .orElseThrow(() -> new ResourceNotFoundException("PersonNatural not found with id " + id));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting personNatural with id: {}", id);
        if (!personNaturalRepository.existsById(id)) {
            throw new ResourceNotFoundException("PersonNatural not found with id " + id);
        }
        personNaturalRepository.deleteById(id);
    }
}
