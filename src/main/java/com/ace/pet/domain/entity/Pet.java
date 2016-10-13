package com.ace.pet.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by shrestar on 2016-09-09.
 */
@Entity
@Table(name = "PET")
public class Pet {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "STATUS")
    private String status;
    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    @JsonManagedReference
    private Category category;

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "pet")
    @JsonManagedReference
    private Set<PetImage> petImages;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pet")
    @JsonManagedReference
    private Set<PetTag> petTags;

    public Pet() {
    }

    public Pet(String name, String status, Category category) {
        this.name = name;
        this.status = status;
        this.category = category;
    }

    public Pet(String name, String status, Category category, Set<PetImage> petImages) {
        this.name = name;
        this.status = status;
        this.category = category;
        this.petImages = petImages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<PetImage> getPetImages() {
        return petImages;
    }

    public void setPetImages(Set<PetImage> petImages) {
        this.petImages = petImages;
    }

    public Set<PetTag> getPetTags() {
        return petTags;
    }

    public void setPetTags(Set<PetTag> petTags) {
        this.petTags = petTags;
    }
}
