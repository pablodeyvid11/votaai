package br.dev.ppaiva.votaAI.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

public abstract class AbstractService {
	protected static final Logger logger = LogManager.getLogger();

	protected static final Gson gson = new Gson();
}
