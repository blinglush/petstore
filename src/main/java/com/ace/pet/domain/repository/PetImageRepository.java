package com.ace.pet.domain.repository;

import com.ace.pet.domain.entity.PetImage;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by shrestar on 2016-09-13.
 */
public interface PetImageRepository extends CrudRepository<PetImage, Long> {
}
