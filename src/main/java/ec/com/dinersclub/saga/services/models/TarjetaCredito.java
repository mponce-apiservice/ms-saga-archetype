package ec.com.dinersclub.saga.services.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import ec.com.dinersclub.saga.orchestrations.transactions.models.Tarjeta;
import ec.com.dinersclub.saga.orchestrations.transactions.models.TarjetaCategory;
import ec.com.dinersclub.saga.orchestrations.transactions.models.TarjetaTags;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

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
    
    public TarjetaCredito(JsonObject tarjeta) {
    	this.id = tarjeta.getInteger("id");
    	this.category = this.getCategory(tarjeta.getJsonObject("category"));
    	this.name = tarjeta.getString("name");
    	this.photoUrls = this.getPhotoUrls(tarjeta.getJsonArray("photoUrls"));
    	this.tags = this.getTags(tarjeta.getJsonArray("tags"));
    	this.status = tarjeta.getString("status");
    }

    public static class TarjetaCreditoCategory {
        public int id;
        public String name;
    }
    
    public static class TarjetaCreditoTags {
        public int id;
        public String name;
    }
    
    private TarjetaCreditoCategory getCategory(TarjetaCategory tarjetaCategory) {
    	TarjetaCreditoCategory category = new TarjetaCreditoCategory();
    	category.id = tarjetaCategory.getId();
    	category.name = tarjetaCategory.getName();
    	return category;
    }
    
    private TarjetaCreditoCategory getCategory(JsonObject tarjetaCategory) {
    	TarjetaCreditoCategory category = new TarjetaCreditoCategory();
    	category.id = tarjetaCategory.getInteger("id");
    	category.name = tarjetaCategory.getString("name");
    	return category;
    }
    
    private List<TarjetaCreditoTags> getTags(List<TarjetaTags> tarjetaTags) {
    	List<TarjetaCreditoTags> tags = new ArrayList<TarjetaCreditoTags>();
    	for(TarjetaTags tag : tarjetaTags) {
    		TarjetaCreditoTags t = new TarjetaCreditoTags();
    		t.id = tag.getId();
    		t.name = tag.getName();
    		tags.add(t);
    	}
    	return tags;
    }
    
    private List<TarjetaCreditoTags> getTags(JsonArray tarjetaTags) {
    	List<TarjetaCreditoTags> tags = new ArrayList<TarjetaCreditoTags>();
    	for(Object tag : tarjetaTags.getList()) {
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