package br.com.swgameapi.backend.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import br.com.swgameapi.backend.data.MongoDbConnection;
import br.com.swgameapi.backend.data.models.Planet;
import br.com.swgameapi.backend.swapi.SwapiIntegration;

import static com.mongodb.client.model.Filters.*;


@Path("planetas")
public class PlanetController {
	private MongoDatabase db = MongoDbConnection.Connect();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPlanets(@QueryParam("nome") String nome) {

		MongoCollection<Planet> planetsDB = db.getCollection("planets", Planet.class);
				
		if (nome == null) {
			Document query = new Document();
			List<Planet> planets = planetsDB.find(query, Planet.class).into(new ArrayList<>());
			return Response.ok(planets).build();
		} else {
			Planet planet = planetsDB.find(eq("nome", nome)).first(); 
			return Response.ok(planet).build();
		}
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertPlanet(Planet planet) throws Exception {
		int filmes = 0;
		SwapiIntegration swApi = new SwapiIntegration("https://swapi.co/api/planets/?search=" + planet.getNome());
		String returnJson = swApi.getResponse();
		JSONObject jsonObject = new JSONObject(returnJson);
		int registros = jsonObject.getInt("count");
		if(registros > 0) {
			JSONArray resultsArray = jsonObject.getJSONArray("results");
			resultsArray = resultsArray.getJSONObject(0).getJSONArray("films");
			filmes = resultsArray.length();
		}
		
		MongoCollection<Planet> planetsDB = db.getCollection("planets", Planet.class);
		planet.setFilmes(filmes);
		planetsDB.insertOne(planet);
			
		String result = "Planeta inserido : " + planet;
		return Response.status(201).entity(result).build();
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/${id}")
	public Response delete(@PathParam("id") ObjectId planetId) {
		MongoCollection<Planet> planetsDB = db.getCollection("planets", Planet.class);
		planetsDB.deleteOne(eq("id:",planetId));
		return Response.status(202).entity("Planeta removido com sucesso!").build();
	}
}
