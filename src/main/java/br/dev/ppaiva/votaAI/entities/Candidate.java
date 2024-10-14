package br.dev.ppaiva.votaAI.entities;

public class Candidate {
	private Long id;
	private String name;
	private Integer number;
	private Election election;

	public Candidate() {
	}

	public Candidate(Long id, String name, Integer number, Election election) {
		this.id = id;
		this.name = name;
		this.number = number;
		this.election = election;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Election getElection() {
		return election;
	}

	public void setElection(Election election) {
		this.election = election;
	}
}