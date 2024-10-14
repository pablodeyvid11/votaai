package br.dev.ppaiva.votaAI.controllers;

import br.dev.ppaiva.votaAI.entities.Vote;
import br.dev.ppaiva.votaAI.entities.records.VoteRecord;
import br.dev.ppaiva.votaAI.server.handler.requests.Path;
import br.dev.ppaiva.votaAI.server.handler.requests.Response;
import br.dev.ppaiva.votaAI.server.types.enums.CodeResponse;
import br.dev.ppaiva.votaAI.server.types.enums.DataMethod;
import br.dev.ppaiva.votaAI.server.types.enums.Status;
import br.dev.ppaiva.votaAI.services.VoteService;

public class VoteController extends AbstractController {

	private VoteService voteService = new VoteService();

	@Path(value = "/vote", method = DataMethod.POST)
	public Response<Vote> vote(String body) {

		VoteRecord vr = null;

		try {
			vr = gson.fromJson(body, VoteRecord.class);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return new Response<>(CodeResponse.ERROR, Status.BAD_REQUEST, e.getMessage());
		}

		Vote v;

		try {
			v = voteService.vote(vr);
		} catch (IllegalArgumentException e) {
			logger.error(e);
			e.printStackTrace();
			return new Response<>(CodeResponse.ERROR, Status.BAD_REQUEST, e.getMessage());
		}

		return new Response<Vote>(CodeResponse.OK, Status.CREATED, v);
	}
}
