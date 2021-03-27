package ec.com.dinersclub.saga.orchestrations.transactions.models;

import java.util.List;

public class Tarjeta {
	
	private int id;
	private TarjetaCategory category;
	private String name;
	private List<String> photoUrls;
	private List<TarjetaTags> tags;
	private String status;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public TarjetaCategory getCategory() {
		return category;
	}
	public void setCategory(TarjetaCategory category) {
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
	public List<TarjetaTags> getTags() {
		return tags;
	}
	public void setTags(List<TarjetaTags> tags) {
		this.tags = tags;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
