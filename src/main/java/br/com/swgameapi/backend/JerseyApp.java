package br.com.swgameapi.backend;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("api")
public class JerseyApp extends ResourceConfig {
	public JerseyApp() {
		packages("br.com.swgameapi.backend.controllers");
	}
}
