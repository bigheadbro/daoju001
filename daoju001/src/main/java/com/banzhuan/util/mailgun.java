package com.banzhuan.util;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class mailgun {

	public static ClientResponse SendSimpleMessage() {
	       Client client = Client.create();
	       client.addFilter(new HTTPBasicAuthFilter("api",
	                       "key-8if7rdtcpu6y22hktddefyin542iwk18"));
	       WebResource webResource =
	               client.resource("https://api.mailgun.net/v2/samples.mailgun.org/messages");
	       MultivaluedMapImpl formData = new MultivaluedMapImpl();
	       formData.add("from", "client@sandbox46141.mailgun.org");
	       formData.add("to", "346938819@qq.com");
	       formData.add("to", "123576884@qq.com");
	       formData.add("subject", "Hello");
	       formData.add("text", "Testing some Mailgun awesomness!");
	       return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).
	               post(ClientResponse.class, formData);
	}
	
	public static void main(String[] args) throws Exception {
		SendSimpleMessage();
	}
}
