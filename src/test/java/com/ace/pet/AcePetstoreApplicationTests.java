package com.ace.pet;

import com.ace.pet.domain.entity.*;
import com.ace.pet.domain.repository.CategoryRepository;
import com.ace.pet.domain.repository.PetRepository;
import com.ace.pet.domain.repository.TagRepository;
import com.ace.pet.service.CategoryService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class AcePetstoreApplicationTests {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private PetRepository petRepository;


	@Autowired private TagRepository tagRepository;



	@Test
	public void contextLoads() {
	}

	@Test
	public void findCategoryByID() {
		Category category = categoryService.findById(2l);
//		Category category1= categoryRepository.save(new Category("fombom"));
		assertEquals(category.getName(),"Cat");

//		assertEquals(entity.getStatusCode(),is(HttpsStatus.found));

	}

	@Test
	public void createPet() {
		Category category = categoryService.findById(2l);
		Pet pet = new Pet("giraf", "a", category);
		Pet retVal = petRepository.save(pet);
		assertEquals(retVal.getName(), "giraf");
	}

	@Test
	@Ignore("method changed")
	public void createPetWithImages() {
		Category category = categoryService.findById(3l);

		Pet pet = new Pet("pigeon", "new", category);

		PetImage image = new PetImage();
		image.setImageUrl("ont.jpg");
		image.setPet(pet);
		PetImage image1 = new PetImage();
		image1.setImageUrl("tor.png");
		image1.setPet(pet);

		Set<PetImage> petImages = new HashSet<PetImage>();
		petImages.add(image);
		petImages.add(image1);

		pet.setPetImages(petImages);
		Pet retVal = petRepository.save(pet);
		assertEquals(retVal.getName(),"pigeon");
	}


	@Test
	@Ignore("method changed")
	public void createPetWithTagsImages() {

		Category category = categoryService.findById(3l);
		Tag tag = tagRepository.findOne(2l);
		Tag tag1 = tagRepository.findOne(4l);

		Pet pet = new Pet("Bear", "available", category);

		PetImage petImage = new PetImage("bear.jpg");
		petImage.setPet(pet);
		PetImage petImage1 = new PetImage("roaming.jpb");
		petImage1.setPet(pet);

		PetTag petTag = new PetTag();
		petTag.setPet(pet);
		petTag.setTag(tag);

		PetTag petTag1 = new PetTag();
		petTag1.setPet(pet);
		petTag1.setTag(tag1);

		Set<PetTag> petTags = new HashSet<>();
		petTags.add(petTag);
		petTags.add(petTag1);

		pet.setPetTags(petTags);

		tag.setPetTags(petTags);

		petRepository.save(pet);
//		tagRepository.save(tag);


	}

	@Test
	@Ignore("don't delete now")
	public void deletePet() {
		Pet pet = petRepository.findOne(4l);
		petRepository.delete(pet.getId());
		Pet pet1 = petRepository.findOne(4l);
		assertNull(pet1);
	}

	@Test
	public void findTagByName() {
		Tag tag = tagRepository.findByName("red");
		assertEquals("red",tag.getName());
	}

	@Test
	public void findPetTagById() {
//		Tag tag = tagRepository.findOne(2l);
		Tag tag = tagRepository.findByName("red");
		tag.getPetTags();
		Set<PetTag> petTagSet = tag.getPetTags();
		List<Pet> pets = new ArrayList<>();
		for (PetTag val : petTagSet) {
			Pet pet = val.getPet();
			pets.add(pet);
		}

		assertEquals(true,pets.size()>0);

	}



}
