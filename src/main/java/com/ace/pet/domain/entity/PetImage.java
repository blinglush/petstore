package com.ace.pet.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by shrestar on 2016-09-13.
 */
@Entity(name = "PET_IMAGE")
public class PetImage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "IMAGE_URL")
    private String imageUrl;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "PET_ID")
    @JsonIgnore
    private Pet pet;

    public PetImage() {
    }

    public PetImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
