package br.dev.ppaiva.votaAI.server.handler;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

import com.google.gson.Gson;

import br.dev.ppaiva.votaAI.server.handler.requests.Response;

public final class UDPMessageHandler extends MessageHandler {

	private DatagramSocket socket;
	private DatagramPacket receivedPacket;

	public UDPMessageHandler(String taskName, DatagramSocket socket, DatagramPacket receivedPacket) {
		super(taskName);
		this.socket = socket;
		this.receivedPacket = receivedPacket;
	}

	@Override
	public void run() {

		String data = new String(receivedPacket.getData());

		String[] tokens = tokenize(data);

		String method = tokens[0];
		String path = tokens[1];
		String body = tokens[2];

		DatagramPacket datagramResponse = null;
		try {
			Response<?> response = (Response<?>) requestDispatcher.dispatch(method, path, body);
			Gson gson = new Gson();

			String bodyResponse = gson.toJson(response).replace("\\u0000", "").trim();

			String responseUDP = String.format("%s\n%d\n\n%s\n", response.getCode().toString(),
					response.getStatus().value(), bodyResponse);

			datagramResponse = new DatagramPacket(responseUDP.getBytes(), responseUDP.length(),
					receivedPacket.getAddress(), receivedPacket.getPort());

		} catch (Exception e) {
			logger.error(e);

			String responseUDP = String.format("ERROR\n\n%s\n", e.getMessage());

			datagramResponse = new DatagramPacket(responseUDP.getBytes(), responseUDP.length(),
					receivedPacket.getAddress(), receivedPacket.getPort());
		}

		try {
			socket.send(datagramResponse);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String[] tokenize(String data) {
		try {
			String[] dataTokens = data.split("\n");

			String body = String.join("\n", Arrays.copyOfRange(dataTokens, 1, dataTokens.length));
			body = body.substring(0, body.lastIndexOf("}") + 1);

			String method = dataTokens[0].split(" ")[0];
			String path = dataTokens[0].split(" ")[1];

			String[] response = { method, path, body };
			return response;
		} catch (Exception e) {
			return null;
		}
	}
}
