package ec.com.dinersclub.saga.services.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.dinersclub.saga.orchestrations.transactions.models.Pet;
import ec.com.dinersclub.saga.orchestrations.transactions.models.PetCategory;
import ec.com.dinersclub.saga.orchestrations.transactions.models.PetTags;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

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
    
    public Petstore(JsonObject pet) {
    	this.id = pet.getInteger("id");
    	this.category = this.getCategory(pet.getJsonObject("category"));
    	this.name = pet.getString("name");
    	this.photoUrls = this.getPhotoUrls(pet.getJsonArray("photoUrls"));
    	this.tags = this.getTags(pet.getJsonArray("tags"));
    	this.status = pet.getString("status");
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
    
    private PetstoreCategory getCategory(JsonObject petCategory) {
    	PetstoreCategory category = new PetstoreCategory();
    	category.id = petCategory.getInteger("id");
    	category.name = petCategory.getString("name");
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
    
    private List<PetstoreTags> getTags(JsonArray petTags) {
    	List<PetstoreTags> tags = new ArrayList<PetstoreTags>();
    	for(Object tag : petTags.getList()) {
    		PetstoreTags t = (PetstoreTags) tag;
    		tags.add(t);
    	}
    	return tags;
    }
    
    private List<String> getPhotoUrls(JsonArray lista) {
    	List<String> urls = new ArrayList<String>();
    	for(Object url : lista.getList()) {
    		urls.add(url.toString());
    	}
    	return urls;
    }
    
    public void addTags(String name) {
    	Random rand = new Random();
    	PetstoreTags t = new PetstoreTags();
    	t.id = rand.nextInt(99);;
    	t.name = name;
    	this.tags.add(t);
    }
    
    public void addTags(Set<Country> country) {
		Iterator<Country> itr = country.iterator();
        while (itr.hasNext()) { 
        	this.addTags(itr.next().name);
        }
    }

}