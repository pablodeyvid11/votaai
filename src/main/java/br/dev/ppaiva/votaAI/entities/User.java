package br.dev.ppaiva.votaAI.entities;

public class User {
	private Long id;
	private String username;
	private String privateKey;

	public User() {
	}

	public User(Long id, String username, String privateKey) {
		this.id = id;
		this.username = username;
		this.privateKey = privateKey;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
}
