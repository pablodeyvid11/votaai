package br.dev.ppaiva.votaAI.controllers;

import java.util.List;
import java.util.Map;

import br.dev.ppaiva.votaAI.entities.Election;
import br.dev.ppaiva.votaAI.entities.records.CreateElectionRecord;
import br.dev.ppaiva.votaAI.entities.records.RequestResultRecord;
import br.dev.ppaiva.votaAI.server.handler.requests.Path;
import br.dev.ppaiva.votaAI.server.handler.requests.Response;
import br.dev.ppaiva.votaAI.server.types.enums.CodeResponse;
import br.dev.ppaiva.votaAI.server.types.enums.DataMethod;
import br.dev.ppaiva.votaAI.server.types.enums.Status;
import br.dev.ppaiva.votaAI.services.ElectionService;

public class ElectionController extends AbstractController {

	private ElectionService electionService = new ElectionService();

	@Path(value = "/election", method = DataMethod.POST)
	public Response<Election> create(String body) {
		CreateElectionRecord cer = null;

		try {
			cer = gson.fromJson(body, CreateElectionRecord.class);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return new Response<>(CodeResponse.ERROR, Status.BAD_REQUEST, e.getMessage());
		}

		Election e;
		try {
			e = electionService.createElection(cer);
		} catch (IllegalArgumentException ex) {
			logger.error(ex);
			ex.printStackTrace();
			return new Response<>(CodeResponse.ERROR, Status.BAD_REQUEST, ex.getMessage());
		}

		return new Response<Election>(CodeResponse.OK, Status.CREATED, e);
	}

	@Path(value = "/election/result", method = DataMethod.GET)
	public Response<Map<Integer, Long>> result(String body) {
		RequestResultRecord rrr = null;

		try {
			rrr = gson.fromJson(body, RequestResultRecord.class);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return new Response<>(CodeResponse.ERROR, Status.BAD_REQUEST, e.getMessage());
		}

		Map<Integer, Long> result;
		try {
			result = electionService.electionResults(rrr.idElection());
		} catch (IllegalArgumentException ex) {
			logger.error(ex);
			ex.printStackTrace();
			return new Response<>(CodeResponse.ERROR, Status.BAD_REQUEST, ex.getMessage());
		}

		return new Response<Map<Integer, Long>>(CodeResponse.OK, Status.CREATED, result);
	}

	@Path(value = "/election", method = DataMethod.GET)
	public Response<List<Election>> findAll(String body) {

		List<Election> elections = electionService.findAllElections();

		return new Response<List<Election>>(CodeResponse.OK, Status.OK, elections);
	}
}
