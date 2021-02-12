package ec.com.dinersclub.saga.services.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.dinersclub.saga.orchestrations.transactions.models.Pet;
import ec.com.dinersclub.saga.orchestrations.transactions.models.PetCategory;
import ec.com.dinersclub.saga.orchestrations.transactions.models.PetTags;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Petstore {

    public int id;
    public PetstoreCategory category;
    public String name;
    public List<String> photoUrls;
    public List<PetstoreTags> tags;
    public String status;
    
    public Petstore() {
    	
    }
    
    public Petstore(Pet pet) {
    	this.id = pet.getId();
    	this.category = this.getCategory(pet.getCategory());
    	this.name = pet.getName();
    	this.photoUrls = pet.getPhotoUrls();
    	this.tags = this.getTags(pet.getTags());
    	this.status = pet.getStatus();
    }

    public static class PetstoreCategory {
        public int id;
        public String name;
    }
    
    public static class PetstoreTags {
        public int id;
        public String name;
    }
    
    private PetstoreCategory getCategory(PetCategory petCategory) {
    	PetstoreCategory category = new PetstoreCategory();
    	category.id = petCategory.getId();
    	category.name = petCategory.getName();
    	return category;
    }
    
    private List<PetstoreTags> getTags(List<PetTags> petTags) {
    	List<PetstoreTags> tags = new ArrayList<PetstoreTags>();
    	for(PetTags tag : petTags) {
    		PetstoreTags t = new PetstoreTags();
    		t.id = tag.getId();
    		t.name = tag.getName();
    		tags.add(t);
    	}
    	return tags;
    }
    
    public void addTags(int id, String name) {
    	PetstoreTags t = new PetstoreTags();
    	t.id = id;
    	t.name = name;
    	this.tags.add(t);
    }

}