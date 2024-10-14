package br.dev.ppaiva.votaAI.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

public abstract class AbstractController {
	protected static final Logger logger = LogManager.getLogger();

	protected static final Gson gson = new Gson();
}
