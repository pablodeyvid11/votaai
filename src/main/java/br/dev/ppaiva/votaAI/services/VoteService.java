package br.dev.ppaiva.votaAI.services;

import br.dev.ppaiva.votaAI.entities.Election;
import br.dev.ppaiva.votaAI.entities.User;
import br.dev.ppaiva.votaAI.entities.Vote;
import br.dev.ppaiva.votaAI.entities.records.VoteRecord;
import br.dev.ppaiva.votaAI.repositories.ElectionRepository;
import br.dev.ppaiva.votaAI.repositories.UserRepository;
import br.dev.ppaiva.votaAI.repositories.VoteRepository;

public class VoteService extends AbstractService {

	private ElectionRepository electionRepository = new ElectionRepository();
	private UserRepository userRepository = new UserRepository();
	private VoteRepository voteRepository = new VoteRepository();

	public Vote vote(VoteRecord voteRecord) throws IllegalArgumentException {

		User u = userRepository.findByPrivateKey(voteRecord.user());

		if (u == null) {
			throw new IllegalArgumentException("Usuário não encontrado.");
		}

		Election election = electionRepository.findById(voteRecord.electionId());
		if (election == null) {
			throw new IllegalArgumentException("Eleição não encontrada.");
		}

		boolean candidateExists = election.getCandidates().stream().anyMatch(candidate -> {
			Integer candidateNumber = voteRecord.candidateNumber();
			return candidate.getNumber().equals(candidateNumber);
		});
		if (!candidateExists) {
			throw new IllegalArgumentException("Número do candidato inválido.");
		}

		Vote vote = new Vote();
		vote.setElection(election);
		vote.setCandidateNumber(voteRecord.candidateNumber());

		voteRepository.save(vote);
		return vote;
	}

}
