package br.dev.ppaiva.votaAI.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.dev.ppaiva.votaAI.entities.Candidate;
import br.dev.ppaiva.votaAI.entities.Election;

public class ElectionRepository extends AbstractRepository {

    public void save(Election election) {
        String sqlElection = "INSERT INTO election (name, user_id) VALUES (?, ?)";
        String sqlCandidate = "INSERT INTO candidate (name, number, election_id) VALUES (?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmtElection = conn.prepareStatement(sqlElection, Statement.RETURN_GENERATED_KEYS)) {

            conn.setAutoCommit(false); // Iniciar transação
            
            // Salvar Election
            stmtElection.setString(1, election.getName());
            stmtElection.setLong(2, election.getResponsible().getId());
            stmtElection.executeUpdate();
            
            try (ResultSet generatedKeys = stmtElection.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    election.setId(generatedKeys.getLong(1));
                }
            }

            // Salvar os candidatos relacionados à eleição
            try (PreparedStatement stmtCandidate = conn.prepareStatement(sqlCandidate)) {
                for (Candidate candidate : election.getCandidates()) {
                    stmtCandidate.setString(1, candidate.getName());
                    stmtCandidate.setInt(2, candidate.getNumber());
                    stmtCandidate.setLong(3, election.getId());
                    stmtCandidate.executeUpdate();
                }
            }

            conn.commit(); // Confirmar transação
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving election", e);
        }
    }

    public Election findById(Long id) {
        String sqlElection = "SELECT election_id, name, user_id FROM election WHERE election_id = ?";
        String sqlCandidates = "SELECT candidate_id, name, number FROM candidate WHERE election_id = ?";
        Election election = null;

        try (Connection conn = getConnection();
             PreparedStatement stmtElection = conn.prepareStatement(sqlElection);
             PreparedStatement stmtCandidates = conn.prepareStatement(sqlCandidates)) {

            // Buscar a eleição
            stmtElection.setLong(1, id);
            try (ResultSet rs = stmtElection.executeQuery()) {
                if (rs.next()) {
                    election = new Election();
                    election.setId(rs.getLong("election_id"));
                    election.setName(rs.getString("name"));
                    // Supondo que você tem um método para buscar o responsável por ID (não mostrado aqui)
                    // election.setResponsible(fetchUserById(rs.getLong("user_id")));
                }
            }

            // Buscar os candidatos relacionados à eleição
            if (election != null) {
                List<Candidate> candidates = new ArrayList<>();
                stmtCandidates.setLong(1, election.getId());
                try (ResultSet rs = stmtCandidates.executeQuery()) {
                    while (rs.next()) {
                        Candidate candidate = new Candidate();
                        candidate.setId(rs.getLong("candidate_id"));
                        candidate.setName(rs.getString("name"));
                        candidate.setNumber(rs.getInt("number"));
                        candidates.add(candidate);
                    }
                }
                election.setCandidates(candidates);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error finding election by ID", e);
        }
        return election;
    }

    public List<Election> findAll() {
        String sqlElection = "SELECT election_id, name, user_id FROM election";
        String sqlCandidates = "SELECT candidate_id, name, number FROM candidate WHERE election_id = ?";
        List<Election> elections = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmtElection = conn.prepareStatement(sqlElection);
             ResultSet rsElection = stmtElection.executeQuery()) {

            while (rsElection.next()) {
                Election election = new Election();
                election.setId(rsElection.getLong("election_id"));
                election.setName(rsElection.getString("name"));
                List<Candidate> candidates = new ArrayList<>();
                
                try (PreparedStatement stmtCandidates = conn.prepareStatement(sqlCandidates)) {
                    stmtCandidates.setLong(1, election.getId());
                    try (ResultSet rsCandidates = stmtCandidates.executeQuery()) {
                        while (rsCandidates.next()) {
                            Candidate candidate = new Candidate();
                            candidate.setId(rsCandidates.getLong("candidate_id"));
                            candidate.setName(rsCandidates.getString("name"));
                            candidate.setNumber(rsCandidates.getInt("number"));
                            candidates.add(candidate);
                        }
                    }
                }

                election.setCandidates(candidates);
                elections.add(election);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error finding all elections with candidates", e);
        }
        return elections;
    }

}
