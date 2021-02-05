package ec.com.dinersclub.saga.orchestrations.transactions.models;

import java.util.List;

public class Pet {
	
	private int id;
	private List<PetCategory> category;
	private String name;
	private List<String> photoUrls;
	private List<PetTags> tags;
	private String status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<PetCategory> getCategory() {
		return category;
	}
	public void setCategory(List<PetCategory> category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getPhotoUrls() {
		return photoUrls;
	}
	public void setPhotoUrls(List<String> photoUrls) {
		this.photoUrls = photoUrls;
	}
	public List<PetTags> getTags() {
		return tags;
	}
	public void setTags(List<PetTags> tags) {
		this.tags = tags;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
