package br.dev.ppaiva.votaAI.server.handler.requests;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.dev.ppaiva.votaAI.server.types.enums.DataMethod;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface Path {
	String value();

	DataMethod method();
}