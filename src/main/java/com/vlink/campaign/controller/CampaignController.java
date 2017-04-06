package com.vlink.campaign.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.vlink.campaign.service.MongoDBService;

@Path("/campaign")
public class CampaignController {

	@GET
	@Produces("application/json")
	public Response getCampaign() {
		int statuscode = 200;
		boolean status = true;
		String message = "Campaign fetch successfuly";
		JSONObject jsonObject = new JSONObject();
		try {
			MongoDBService mongodbservice = new MongoDBService();
			List<DBObject> dbObjects = mongodbservice.get("campaign");
			jsonObject.put("campaignList", dbObjects);			
		} catch (Exception e) {
			status = false;
			message = "Unable to process request";
			statuscode = 500;
		}

		jsonObject.put("status", status);
		jsonObject.put("message", message);
		Response response = Response.status(statuscode).entity(jsonObject.toJSONString()).build();
		return response;
	}

	@GET
	@Produces("application/json")
	@Path("/{id}")
	public Response getCampaign(@PathParam("id") String id) {
		int statuscode = 200;
		boolean status = true;
		String message = "Campaign fetch successfuly";
		JSONObject jsonObject = new JSONObject();
		try {
			MongoDBService mongodbservice = new MongoDBService();
			DBObject dbObject = mongodbservice.findById("campaign", id);
			jsonObject.put("campaign", dbObject);
		} catch (Exception e) {
			status = false;
			message = "Unable to process request";
			statuscode = 500;
			System.out.println(e);
		}
		jsonObject.put("status", status);
		jsonObject.put("message", message);
		System.out.println(jsonObject.toJSONString());
		Response response = Response.status(statuscode).entity(jsonObject.toJSONString()).build();
		return response;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded")
	public Response postCampaign(MultivaluedMap<String, String> campaignData) {
		int statuscode = 200;
		boolean status = true;
		String message = "Campaign created successfuly";
		JSONObject jsonObject = new JSONObject();
		try {
			Map<String, String> data = new HashMap<String, String>();
			for (Map.Entry<String, List<String>> entry : campaignData.entrySet()) {
				data.put(entry.getKey(), entry.getValue().get(0));
			}
			MongoDBService mongodbservice = new MongoDBService();
			mongodbservice.insert("campaign", data);
		} catch (Exception e) {
			status = false;
			message = "Unable to process request";
			statuscode = 500;
		}
		jsonObject.put("status", status);
		jsonObject.put("message", message);
		Response response = Response.status(statuscode).entity(jsonObject.toJSONString()).build();
		return response;
	}

	/*@PUT
	@Produces("application/json")
	@Consumes("application/x-www-form-urlencoded")
	@Path("/{id}")
	public Response putCampaign(MultivaluedMap<String, String> campaignData, @PathParam("id") String id) {
		int statuscode = 200;
		boolean status = true;
		String message = "Campaign created successfuly";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		try {
			Map<String, String> data = new HashMap<String, String>();
			for (Map.Entry<String, List<String>> entry : campaignData.entrySet()) {
				data.put(entry.getKey(), entry.getValue().get(0));
			}
			MongoDBService mongodbservice = new MongoDBService();
			mongodbservice.update("campaign", data);
		} catch (Exception e) {
			// TODO: handle exception
			status = false;
			message = "Unable to process request";
			statuscode = 500;

		}
		jsonObject.put("status", status);
		jsonObject.put("message", message);
		Response response = Response.status(statuscode).entity(jsonObject.toJSONString()).build();
		return response;
	}
	
	@DELETE
	@Produces("application/json")
	@Path("/{id}")*/

}
