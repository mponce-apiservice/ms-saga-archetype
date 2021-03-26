package ec.com.dinersclub.saga.services.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import ec.com.dinersclub.saga.orchestrations.transactions.models.Tarjeta;
import ec.com.dinersclub.saga.orchestrations.transactions.models.TarjetaTags;
import ec.com.dinersclub.saga.orchestrations.transactions.models.TarjetaCategory;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TarjetaCredito {

    public int id;
    public TarjetaCreditoCategory category;
    public String name;
    public List<String> photoUrls;
    public List<TarjetaCreditoTags> tags;
    public String status;
    
    public TarjetaCredito() {
    	
    }
    
    public TarjetaCredito(Tarjeta tarjeta) {
    	this.id = tarjeta.getId();
    	this.category = this.getCategory(tarjeta.getCategory());
    	this.name = tarjeta.getName();
    	this.photoUrls = tarjeta.getPhotoUrls();
    	this.tags = this.getTags(tarjeta.getTags());
    	this.status = tarjeta.getStatus();
    }
    
    public TarjetaCredito(JsonObject obj) {
    	this.id = obj.getInteger("id");
    	this.category = this.getCategory(obj.getJsonObject("category"));
    	this.name = obj.getString("name");
    	this.photoUrls = this.getPhotoUrls(obj.getJsonArray("photoUrls"));
    	this.tags = this.getTags(obj.getJsonArray("tags"));
    	this.status = obj.getString("status");
    }

    public static class TarjetaCreditoCategory {
        public int id;
        public String name;
    }
    
    public static class TarjetaCreditoTags {
        public int id;
        public String name;
    }
    
    private TarjetaCreditoCategory getCategory(TarjetaCategory tCategory) {
    	TarjetaCreditoCategory category = new TarjetaCreditoCategory();
    	category.id = tCategory.getId();
    	category.name = tCategory.getName();
    	return category;
    }
    
    private TarjetaCreditoCategory getCategory(JsonObject objCategory) {
    	TarjetaCreditoCategory category = new TarjetaCreditoCategory();
    	category.id = objCategory.getInteger("id");
    	category.name = objCategory.getString("name");
    	return category;
    }
    
    private List<TarjetaCreditoTags> getTags(List<TarjetaTags> petTags) {
    	List<TarjetaCreditoTags> tags = new ArrayList<TarjetaCreditoTags>();
    	for(TarjetaTags tag : petTags) {
    		TarjetaCreditoTags t = new TarjetaCreditoTags();
    		t.id = tag.getId();
    		t.name = tag.getName();
    		tags.add(t);
    	}
    	return tags;
    }
    
    private List<TarjetaCreditoTags> getTags(JsonArray arrTags) {
    	List<TarjetaCreditoTags> tags = new ArrayList<TarjetaCreditoTags>();
    	for(Object tag : arrTags.getList()) {
    		TarjetaCreditoTags t = (TarjetaCreditoTags) tag;
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
    	TarjetaCreditoTags t = new TarjetaCreditoTags();
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
