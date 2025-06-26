package com.example.stringconverter.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class EcsTaskIdUtil {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@PostConstruct
	public void init() {
		try {
			log.info("get all env : start");
			System.getenv().forEach((k, v) -> {
				log.info("{} = {}", k, v);
			});
			log.info("get all env : end");

			String metadataUri = System.getenv("ECS_CONTAINER_METADATA_URI_V4");
			log.info("ECS_CONTAINER_METADATA_URI_V4" + metadataUri);

			if (metadataUri == null) {
				metadataUri = System.getenv("ECS_CONTAINER_METADATA_URI");
				log.info("ECS_CONTAINER_METADATA_URI" + metadataUri);
			}

			if (metadataUri != null) {

				URL url = new URL(metadataUri + "/task");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");

				try (InputStream in = conn.getInputStream()) {

					ObjectMapper mapper = new ObjectMapper();
					JsonNode root = mapper.readTree(in);
					String taskArn = root.path("TaskARN").asText();
					String[] parts = taskArn.split("/");
					String taskId = parts[parts.length - 1];

					// MDC.put("ecsTaskId", taskId);
				}
			} else {
				// MDC.put("ecsTaskId", "unknown-task");

			}
		} catch (Exception e) {
			log.error("Failed to fetch ECS task ID: " + e.getMessage());
		}
	}
}
