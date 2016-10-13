package com.ace.pet.service;

import com.ace.pet.domain.entity.Pet;
import com.ace.pet.domain.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by shrestar on 2016-09-10.
 */
@Service
public class PetServiceImpl implements PetService {

    @Autowired
    PetRepository petRepository;

    public Pet savePet(Pet pet) {
        return petRepository.save(pet);
    }

    public void deletePet(Long id) {
        petRepository.delete(id);
    }

    public Pet findPet(Long id) {
        return petRepository.findById(id);
    }
}
