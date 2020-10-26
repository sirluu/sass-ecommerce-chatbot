package com.luu.telemed.handlers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;

@Component
public class SocketHandler extends TextWebSocketHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(SocketHandler.class);

	List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
	Map<String, UserConnection> clients = new HashMap<String, UserConnection>();

	@Override
	public void handleTextMessage(final WebSocketSession session, final TextMessage message) {
		LOGGER.info("you have {} {}", this.sessions.size(), " WebSocket Sessions Open!!!");
		LOGGER.info("you have {} {}", this.clients.size(), " WebSocket Sessions From Map");
		LOGGER.info("Session HandelTextMessage {}", session.toString());
		LOGGER.info("Text message Pyload !!!!!! {}", message.getPayload());
		final JacksonJsonParser jsonParser = new JacksonJsonParser();
		final Map<String, Object> payload = jsonParser.parseMap(message.getPayload());
		if (payload != null) {
			LOGGER.info("Parsed Payload : {}", payload.toString());
			String calleeId = String.valueOf(payload.get("calleeId"));
			LOGGER.info("Callee ID : {}", calleeId);
			UserConnection calleeConnection = clients.get(calleeId);
			if (calleeConnection == null) {
				LOGGER.info("!!!!! Client {} {}", calleeId, " IS NOT CONNECTED");
				String errorPayload = "";
				payload.put("error", "client" + calleeId + " not Connected");
				ObjectMapper mapper = new ObjectMapper();
				try {
					errorPayload = mapper.writeValueAsString(payload);
					session.sendMessage(new TextMessage(errorPayload));
				} catch (JsonProcessingException ex) {
					LOGGER.error("JsonProcessingException >>> {}", ex.getMessage());
				} catch (IOException ex) {
					LOGGER.error("IOException >>> {}", ex.getMessage());
				}
				return;
			}
			WebSocketSession calleeSession = calleeConnection.getSession();
			if (calleeSession.isOpen()) {
				try {
					calleeSession.sendMessage(message);
				} catch (IOException ex) {
					LOGGER.error("IOException >>> {}", ex.getMessage());
				}
			}
		}
	}

	@Override
	public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
		LOGGER.info("after Connect Session QUERRY !!! {}", session.getUri().getQuery());
		final MultiValueMap<String, String> queries = UriComponentsBuilder.fromUri(session.getUri()).build()
				.getQueryParams();
		final String userId = queries.get("userId").get(0);
		LOGGER.info("UserID !!!!!!!!!!!!!!!!!!!! {}", userId);
		if (userId != null) {
			clients.put(userId, new UserConnection(userId, session));
		}
		sessions.add(session);
	}

	@Getter
	class UserConnection {
		private final String userId;
		private final WebSocketSession session;
		UserConnection(String userId, WebSocketSession session) {
			this.userId = userId;
			this.session = session;
		}
	}
}
