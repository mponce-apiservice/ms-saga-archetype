package ec.com.dinersclub.saga.services.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PetstoreDelete {

	public int code;
	public String type;
	public String message;
	
}
