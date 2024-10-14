package br.dev.ppaiva.votaAI.entities;

public class Vote {
	private Long id;
	private Election election;
	private Integer candidateNumber;

	public Vote() {
	}

	public Vote(Long id, Election election, Integer candidateNumber) {
		this.id = id;
		this.election = election;
		this.candidateNumber = candidateNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Election getElection() {
		return election;
	}

	public void setElection(Election election) {
		this.election = election;
	}

	public Integer getCandidateNumber() {
		return candidateNumber;
	}

	public void setCandidateNumber(Integer candidateNumber) {
		this.candidateNumber = candidateNumber;
	}

}
