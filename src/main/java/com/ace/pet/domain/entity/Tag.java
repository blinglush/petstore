package com.ace.pet.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by shrestar on 2016-09-10.
 */
@Entity(name = "TAG")
public class Tag {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "tag")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private Set<PetTag> petTags;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
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

    public Set<PetTag> getPetTags() {
        return petTags;
    }

    public void setPetTags(Set<PetTag> petTags) {
        this.petTags = petTags;
    }
}
