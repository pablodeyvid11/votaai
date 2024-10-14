package br.dev.ppaiva.votaAI.controllers;

import br.dev.ppaiva.votaAI.server.handler.requests.Path;
import br.dev.ppaiva.votaAI.server.handler.requests.Response;
import br.dev.ppaiva.votaAI.server.types.enums.CodeResponse;
import br.dev.ppaiva.votaAI.server.types.enums.DataMethod;
import br.dev.ppaiva.votaAI.server.types.enums.Status;

public class HealthCheck extends AbstractController {

	@Path(value = "/health", method = DataMethod.GET)
	public Response<String> healthCheck(String body) {
		return new Response<>(CodeResponse.OK, Status.OK, "Healthy");
	}
}
