package com.ace.pet.domain.repository;

import com.ace.pet.domain.entity.Pet;
import com.ace.pet.domain.entity.PetTag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by shrestar on 2016-09-10.
 */

public interface PetRepository extends CrudRepository<Pet, Long> {

    Pet findById(Long id);



}
