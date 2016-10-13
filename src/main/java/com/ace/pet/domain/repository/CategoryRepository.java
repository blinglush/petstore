package com.ace.pet.domain.repository;

import com.ace.pet.domain.entity.Category;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by shrestar on 2016-09-10.
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Category findById(Long id);


}

