package com.ace.pet.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by shrestar on 2016-09-14.
 */
@Entity(name="PET_TAG")
public class PetTag  implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "PET_ID")
    @JsonIgnore
    private Pet pet;

    @Id
    @ManyToOne
    @JoinColumn(name = "TAG_ID")
    private Tag tag;

    public PetTag() {
    }

    public PetTag(Pet pet, Tag tag) {
        this.pet = pet;
        this.tag = tag;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
