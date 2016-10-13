package com.ace.pet.service;

import com.ace.pet.domain.entity.Pet;

import java.util.List;

/**
 * Created by shrestar on 2016-09-09.
 */
public interface PetService {

    Pet savePet(Pet pet);

    void deletePet(Long id);

    Pet findPet(Long id);
}
