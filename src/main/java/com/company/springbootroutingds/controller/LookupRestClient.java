package com.company.springbootroutingds.controller;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import com.company.springbootroutingds.config.Query;

public class LookupRestClient {
	
	public static String callLookupService(String queryString)
	{
		Client client = ClientBuilder.newClient(new ClientConfig());
		WebTarget webTarget = client.target("http://localhost:8081/lookupservice").queryParam("queryString", queryString);
		
		Invocation.Builder invocationBulider = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBulider.get();
		
		Query lookupResponseQuery = response.readEntity(Query.class);
		
		return lookupResponseQuery.getQuery();

}
}
