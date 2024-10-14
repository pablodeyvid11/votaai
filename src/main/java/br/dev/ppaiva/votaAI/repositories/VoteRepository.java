package br.dev.ppaiva.votaAI.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.dev.ppaiva.votaAI.entities.Vote;

public class VoteRepository extends AbstractRepository {

	public void save(Vote vote) {
		String sql = "INSERT INTO vote (election_id, candidate_number) VALUES (?, ?)";
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setLong(1, vote.getElection().getId());
			stmt.setInt(2, vote.getCandidateNumber());

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error saving vote", e);
		}
	}

	public List<Vote> findByElectionId(Long electionId) {
		String sql = "SELECT vote_id, election_id, candidate_number FROM vote WHERE election_id = ?";
		List<Vote> votes = new ArrayList<>();

		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setLong(1, electionId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Vote vote = new Vote();
				vote.setId(rs.getLong("vote_id"));
				// Aqui assumimos que você já buscou a eleição e tem um Election no repositório
				// vote.setElection(fetchElectionById(rs.getLong("election_id")));
				vote.setCandidateNumber(rs.getInt("candidate_number"));
				votes.add(vote);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error fetching votes by election id", e);
		}

		return votes;
	}
}
