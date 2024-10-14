package br.dev.ppaiva.votaAI.entities;

import java.util.List;

public class Election {
	private Long id;
	private String name;
	private List<Candidate> candidates;
	private User responsible;

	public Election() {
	}

	public Election(Long id, String name, List<Candidate> candidates, User responsible) {
		this.id = id;
		this.name = name;
		this.candidates = candidates;
		this.responsible = responsible;
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

	public List<Candidate> getCandidates() {
		return candidates;
	}

	public void setCandidates(List<Candidate> candidates) {
		this.candidates = candidates;
	}

	public User getResponsible() {
		return responsible;
	}

	public void setResponsible(User responsible) {
		this.responsible = responsible;
	}

}
