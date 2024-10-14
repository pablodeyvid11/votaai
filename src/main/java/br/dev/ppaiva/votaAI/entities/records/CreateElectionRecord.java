package br.dev.ppaiva.votaAI.entities.records;

import java.util.List;

import br.dev.ppaiva.votaAI.entities.Candidate;

public record CreateElectionRecord(String user, String name, List<Candidate> candidates) {

}
