package br.dev.ppaiva.votaAI;

import br.dev.ppaiva.votaAI.server.factory.ServerFactory;
import br.dev.ppaiva.votaAI.server.handler.requests.RequestDispatcher;
import br.dev.ppaiva.votaAI.server.types.enums.ServerType;

public class VotaAIApplication {

	public static void main(String[] args) {

		if (args.length < 2) {
			System.err.println("USAGE: VotaAIApplication [PORT] [UDP/TCP/HTTP]");
			System.exit(1);
		}

		int port = 0;
		ServerType serverType = null;

		try {
			port = Integer.parseInt(args[0]);
			serverType = ServerType.valueOf(args[1]);
		} catch (Exception e) {
			System.err.println("USAGE: VotaAIApplication [PORT] [UDP/TCP/HTTP]");
			System.exit(1);
		}

		RequestDispatcher rd = RequestDispatcher.getInstance();
		rd.loadMethods("br.dev.ppaiva.votaAI.controllers");
		rd.printMethods();

		ServerFactory.build(port, serverType).start();
	}
}
