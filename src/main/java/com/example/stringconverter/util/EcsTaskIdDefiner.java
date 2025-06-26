package com.example.stringconverter.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.core.PropertyDefinerBase;

public class EcsTaskIdDefiner extends PropertyDefinerBase {

	@Override
	public String getPropertyValue() {
		try {

			String metadataUri = System.getenv("ECS_CONTAINER_METADATA_URI_V4");

			if (metadataUri == null) {
				metadataUri = System.getenv("ECS_CONTAINER_METADATA_URI");

			}

			if (metadataUri != null) {

				URL url = new URL(metadataUri + "/task");
				HttpURLConnection conn = (HttpURLConnection)url
						.openConnection();
				conn.setRequestMethod("GET");

				try (InputStream in = conn.getInputStream()) {

					ObjectMapper mapper = new ObjectMapper();
					JsonNode root = mapper.readTree(in);
					String taskArn = root.path("TaskARN").asText();
					String[] parts = taskArn.split("/");
					String taskId = parts[parts.length - 1];

					return taskId;
				}
			} else {

				return "unknown-taskId";

			}
		} catch (Exception e) {
			return "err-taskId";
		}
	}

}
