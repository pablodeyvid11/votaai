package br.dev.ppaiva.votaAI.server.factory;

import br.dev.ppaiva.votaAI.server.Server;
import br.dev.ppaiva.votaAI.server.http.HTTPServer;
import br.dev.ppaiva.votaAI.server.tcp.TCPServer;
import br.dev.ppaiva.votaAI.server.types.enums.ServerType;
import br.dev.ppaiva.votaAI.server.udp.UDPServer;

public class ServerFactory {
	public static Server build(int port, ServerType type) {

		switch (type) {
		case HTTP:
			return new HTTPServer(port);
		case TCP:
			return new TCPServer(port);
		case UDP:
			return new UDPServer(port);
		default:
			throw new IllegalArgumentException("Invalid server type");
		}
	}
}
