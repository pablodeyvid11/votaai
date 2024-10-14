package br.dev.ppaiva.votaAI.services;

import br.dev.ppaiva.votaAI.entities.User;
import br.dev.ppaiva.votaAI.entities.records.RegisterRecord;
import br.dev.ppaiva.votaAI.repositories.UserRepository;

public class UserService extends AbstractService {
	private UserRepository userRepository = new UserRepository();

	public User save(RegisterRecord registerRecord) throws IllegalArgumentException {
		User uExists = userRepository.findByUsername(registerRecord.username());

		if (uExists != null) {
			throw new IllegalArgumentException("Username " + registerRecord.username() + " aready exists!");
		}

		User u = new User();
		u.setUsername(registerRecord.username());

		u = userRepository.save(u);

		u.setPrivateKey(u.hashCode() + "" + u.getId());

		u = userRepository.update(u);
		return u;
	}
}
