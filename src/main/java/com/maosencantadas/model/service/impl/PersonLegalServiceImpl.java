package com.maosencantadas.model.service.impl;

import com.maosencantadas.api.mapper.PersonLegalMapper;
import com.maosencantadas.exception.ResourceNotFoundException;
import com.maosencantadas.model.domain.person.PersonLegal;
import com.maosencantadas.model.domain.person.PersonLegal;
import com.maosencantadas.model.repository.PersonLegalRepository;
import com.maosencantadas.model.service.ArtistService;
import com.maosencantadas.model.service.PersonLegalService;
import com.maosencantadas.model.service.PersonLegalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonLegalServiceImpl implements PersonLegalService {

    private final PersonLegalRepository personLegalRepository;
    private final ArtistService artistService;

    @Override
    public List<PersonLegal> findAll() {
        log.info("Listing all personLegals with artist and category");
        return personLegalRepository.findAll();
    }

    @Override
    public PersonLegal findById(Long id) {
        log.info("Finding personLegal by id: {}", id);
        return personLegalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PersonLegal not found with id " + id));
    }

    @Override
    public PersonLegal save(PersonLegal personLegal) {
        log.info("Saving new personLegal");
        return personLegalRepository.save(personLegal);
    }

    @Override
    public PersonLegal update(Long id, PersonLegal personLegal) {
        log.info("Updating personLegal with id: {}", id);
        return personLegalRepository.findById(id)
                .map(existingPersonLegal -> {
                    existingPersonLegal.setName(personLegal.getName());
                    existingPersonLegal.setEmail(personLegal.getEmail());
                    existingPersonLegal.setPhone(personLegal.getPhone());
                    existingPersonLegal.setWhatsapp(personLegal.getWhatsapp());
                    existingPersonLegal.setAddress(personLegal.getAddress());
                    existingPersonLegal.setAddress(personLegal.getAddress());
                    existingPersonLegal.setDocumentNumber(personLegal.getDocumentNumber());
                    existingPersonLegal.setLegalRepresentative(personLegal.getLegalRepresentative());
                    existingPersonLegal.setLegalRepresentativePhone(personLegal.getLegalRepresentativePhone());
                    return personLegalRepository.save(existingPersonLegal);
                })
                .orElseThrow(() -> new ResourceNotFoundException("PersonLegal not found with id " + id));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting personLegal with id: {}", id);
        if (!personLegalRepository.existsById(id)) {
            throw new ResourceNotFoundException("PersonLegal not found with id " + id);
        }
        personLegalRepository.deleteById(id);
    }
}
