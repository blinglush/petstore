package com.ace.pet.service;

import com.ace.pet.domain.entity.Category;
import com.ace.pet.domain.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by shrestar on 2016-09-10.
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;


    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id);
    }
}
