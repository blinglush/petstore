package com.ace.pet.domain.repository;

import com.ace.pet.domain.entity.Pet;
import com.ace.pet.domain.entity.PetTag;
import com.ace.pet.domain.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by shrestar on 2016-09-10.
 */
public interface TagRepository extends JpaRepository<Tag,Long> {

    Tag findByName(String name);

}
