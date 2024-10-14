package br.dev.ppaiva.votaAI.controllers;

import br.dev.ppaiva.votaAI.entities.User;
import br.dev.ppaiva.votaAI.entities.records.RegisterRecord;
import br.dev.ppaiva.votaAI.server.handler.requests.Path;
import br.dev.ppaiva.votaAI.server.handler.requests.Response;
import br.dev.ppaiva.votaAI.server.types.enums.CodeResponse;
import br.dev.ppaiva.votaAI.server.types.enums.DataMethod;
import br.dev.ppaiva.votaAI.server.types.enums.Status;
import br.dev.ppaiva.votaAI.services.UserService;

public class UserController extends AbstractController {

	private UserService userService = new UserService();

	@Path(value = "/user", method = DataMethod.POST)
	public Response<User> create(String body) {

		RegisterRecord rr = null;

		try {
			rr = gson.fromJson(body, RegisterRecord.class);
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
			return new Response<>(CodeResponse.ERROR, Status.BAD_REQUEST, e.getMessage());
		}

		User u;
		try {
			u = userService.save(rr);
		} catch (IllegalArgumentException e) {
			logger.error(e);
			e.printStackTrace();
			return new Response<>(CodeResponse.ERROR, Status.BAD_REQUEST, e.getMessage());
		}

		return new Response<User>(CodeResponse.OK, Status.CREATED, u);
	}
}
