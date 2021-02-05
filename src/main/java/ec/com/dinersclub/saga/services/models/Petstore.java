package ec.com.dinersclub.saga.services.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Petstore {

    public int id;
    public List<PetstoreCategory> category;
    public String name;
    public List<String> photoUrls;
    public List<PetstoreTags> tags;
    public String status;

    public static class PetstoreCategory {
        public int id;
        public String name;
    }
    
    public static class PetstoreTags {
        public int id;
        public String name;
    }

}