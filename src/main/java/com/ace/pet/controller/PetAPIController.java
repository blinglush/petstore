package com.ace.pet.controller;

import com.ace.pet.config.ResourceConfig;
import com.ace.pet.domain.entity.*;
import com.ace.pet.domain.repository.CategoryRepository;
import com.ace.pet.domain.repository.PetRepository;
import com.ace.pet.domain.repository.TagRepository;
import com.ace.pet.service.CategoryService;
import com.ace.pet.service.PetService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by shrestar on 2016-09-09.
 */

//@CrossOrigin(origins = "http://localhost:63342/")
@CrossOrigin
@RestController
public class PetAPIController {

    @Autowired
    PetService petService;

    @Autowired
    PetRepository petRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ResourceConfig config;


//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public String homepage() {
//        return "index";
//    }

    private static Log logger = LogFactory.getLog(PetAPIController.class);


    @GetMapping(value = "/pet")
    public ResponseEntity getPets() {
        List<Pet> pets = (List<Pet>) petRepository.findAll();
        if (pets != null && pets.size() > 0) {
            Map<String, Object> petMap = new HashMap<>();
            petMap.put("data", pets);
            return new ResponseEntity(petMap, HttpStatus.OK);
        }

        return new ResponseEntity("No Pets", HttpStatus.OK);
    }

    @PostMapping(value = "/pet")
    public ResponseEntity savePet(@RequestBody Map<String, Object> request) {

        Pet pet = new Pet();

        String petName = null;
        Category category = null;
        String status = null;

        if (request.get("name") != null) {
            petName = (String) request.get("name");
            pet.setName(petName);
        } else {
            return new ResponseEntity("Invalid Input", HttpStatus.METHOD_NOT_ALLOWED);
        }

        if (request.get("category") != null) {
            Integer catId = (Integer) ((Map) request.get("category")).get("id");
            category = categoryService.findById(catId.longValue());
            pet.setCategory(category);
        }
        if (request.get("status") != null) {
            status = (String) request.get("status");
            pet.setStatus(status);
        }
        if (request.get("photoUrls") != null) {

            Set<PetImage> petImg = new HashSet<>();
            List<String> imgs = (List<String>) request.get("photoUrls");
            for (String val : imgs) {
                System.out.println("imageName: "+val);
                String imageUrl = createFilePath(null, val, false);

                PetImage p = new PetImage(imageUrl);
                p.setPet(pet);
                petImg.add(p);
            }
            pet.setPetImages(petImg);



        }

        if (request.get("tags") != null) {
            Set<PetTag> petTags = new HashSet<>();

            List<Map> map = (List) request.get("tags");
            List<Long> ids = new ArrayList<>();

            for (Map<String, Object> val : map) {
                Integer id = (Integer) val.get("id");
                ids.add(id.longValue());
            }
            List<Tag> tags = tagRepository.findAll(ids);
            for (Tag tag : tags) {
                PetTag pt = new PetTag(pet, tag);
                petTags.add(pt);
            }
            pet.setPetTags(petTags);
        }

        Pet pet3 = petService.savePet(pet);

        return new ResponseEntity(pet3, HttpStatus.OK);


    }

    @GetMapping("pet/{petId}")
    public ResponseEntity getPet(@PathVariable String petId) {

        Pet retVal = null;
        try {
            retVal = petService.findPet(Long.parseLong(petId));
            if (retVal == null) {
                return new ResponseEntity("Pet not found", HttpStatus.NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return new ResponseEntity("Invalid ID supplied", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(retVal, HttpStatus.OK);

    }

    @DeleteMapping("/pet/{petId}")
    public ResponseEntity deletePet(@PathVariable String petId) {

        System.out.println("Delete method called");

        try {
            if (null == petService.findPet(Long.parseLong(petId))) {
                return new ResponseEntity("Pet not found", HttpStatus.NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            return new ResponseEntity("Invalid ID supplied", HttpStatus.BAD_REQUEST);
        }

        petService.deletePet(Long.parseLong(petId));
        return null;
    }

    @GetMapping(value = "/pet/load")
    public ResponseEntity loadData() {

        ArrayList<Category> categories = (ArrayList<Category>) categoryRepository.findAll();
        ArrayList<Tag> tags = (ArrayList<Tag>) tagRepository.findAll();

        List<String> status = new ArrayList<>();
        status.add("Available");
        status.add("Pending");
        status.add("Sold");

        Map<String, Object> retVal = new HashMap<>();
        retVal.put("status", status);
        retVal.put("categories", categories);
        retVal.put("tags", tags);

        return new ResponseEntity(retVal, HttpStatus.OK);

    }

    @PostMapping("/pet/{petId}/uploadImage")
    public Map<String, Object> uploadImage(@PathVariable String petId, @RequestParam("file") MultipartFile multipartFile) {
        System.out.println("Image Upload Controller");
        Map<String, Object> retVal = new HashMap<>();
        boolean imageSaved = saveImageInFile(petId, multipartFile);
        if (imageSaved) {
            retVal.put("code", 200);
            retVal.put("type", "type");
            retVal.put("message", "Successfull Upload");
        } else {
            retVal.put("code", 0);
            retVal.put("type", "type");
            retVal.put("message", "Failed");
        }

        return retVal;
    }

    private boolean saveImageInFile(String petId, MultipartFile multipartFile) {

        String fileName = multipartFile.getOriginalFilename();
        boolean imageCreated = false;

        File imgPath = new File(createFilePath(petId, fileName, true));
        boolean dirCreated = imgPath.getParentFile().mkdirs();

            try {
                multipartFile.transferTo(imgPath);
                imageCreated = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        System.out.println(imgPath.getAbsolutePath());

        return imageCreated;

    }

    private String createFilePath(String petId, String fileName, boolean isUpload) {

        DateTime dt = new DateTime();
        int year = dt.getYear();
        String path;

        if (isUpload) {
            String imageDir = "/opt/petstore";
            String yearLocation = imageDir + "/" + Integer.toString(year);
//            path = yearLocation + "/" + petId + "/" + fileName;
            path = yearLocation + "/" + petId + "_" + fileName;

        } else {
            String yearLocation = "/" + Integer.toString(year);
             path = yearLocation + "/" + fileName;
        }

        return path;
    }


}
