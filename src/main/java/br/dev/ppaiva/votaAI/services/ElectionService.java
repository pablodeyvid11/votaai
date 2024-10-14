package br.dev.ppaiva.votaAI.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;

import br.dev.ppaiva.votaAI.entities.Candidate;
import br.dev.ppaiva.votaAI.entities.Election;
import br.dev.ppaiva.votaAI.entities.User;
import br.dev.ppaiva.votaAI.entities.Vote;
import br.dev.ppaiva.votaAI.entities.records.CreateElectionRecord;
import br.dev.ppaiva.votaAI.repositories.ElectionRepository;
import br.dev.ppaiva.votaAI.repositories.UserRepository;
import br.dev.ppaiva.votaAI.repositories.VoteRepository;

public class ElectionService extends AbstractService {

	private ElectionRepository electionRepository = new ElectionRepository();
	private UserRepository userRepository = new UserRepository();
	private VoteRepository voteRepository = new VoteRepository();

	public Election createElection(CreateElectionRecord electionRecord) throws IllegalArgumentException {
		if (electionRecord.name() == null || electionRecord.name().trim().isEmpty()) {
			throw new IllegalArgumentException("O nome da eleição não pode ser nulo ou vazio.");
		}
		List<Candidate> candidates = electionRecord.candidates();
		if (candidates == null || candidates.isEmpty()) {
			throw new IllegalArgumentException("A eleição deve ter pelo menos um candidato.");
		}

		for (Candidate candidate : candidates) {
			if (candidate.getName() == null || candidate.getName().trim().isEmpty()) {
				throw new IllegalArgumentException("O nome do candidato não pode ser nulo ou vazio.");
			}
			if (candidate.getNumber() == null || candidate.getNumber() <= 0) {
				throw new IllegalArgumentException("O número do candidato deve ser um valor positivo.");
			}
		}

		User responsibleUser = userRepository.findByPrivateKey(electionRecord.user());
		if (responsibleUser == null) {
			throw new IllegalArgumentException("Usuário responsável não encontrado.");
		}

		Election election = new Election();
		election.setName(electionRecord.name());
		election.setCandidates(candidates);
		election.setResponsible(responsibleUser);

		electionRepository.save(election);

		return election;
	}

	public Map<Integer, Long> electionResults(Long electionId) {
		Election election = electionRepository.findById(electionId);
		if (election == null) {
			throw new IllegalArgumentException("Eleição não encontrada.");
		}

		List<Vote> votes = voteRepository.findByElectionId(electionId);

		return votes.stream().collect(Collectors.groupingBy(Vote::getCandidateNumber, Collectors.counting()));
	}

	@Transactional
	public List<Election> findAllElections() {
		List<Election> elections = electionRepository.findAll();
		return elections;
	}
}
