package br.com.swgameapi.backend.data.models;

import org.bson.types.ObjectId;

public class Planet {

	
	private ObjectId id;
	private String nome;
	private String terreno;
	private String clima;
	private int filmes;
		
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTerreno() {
		return terreno;
	}

	public void setTerreno(String terreno) {
		this.terreno = terreno;
	}

	public String getClima() {
		return clima;
	}

	public void setClima(String clima) {
		this.clima = clima;
	}

	public int getFilmes() {
		return filmes;
	}

	public void setFilmes(int filmes) {
		this.filmes = filmes;
	}

	@Override
	public String toString() {
		return "{nome:" + nome + ", terreno:" + terreno + ", clima:" + clima + ", filmes:" + filmes + "}";
	}
	
}
