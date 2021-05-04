package com.system.controller;

import com.configuration.WebClientConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.system.model.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/api/securities")
public class SecuritiesController {
    JacksonXmlModule module;
    XmlMapper xmlMapper;

    @Autowired
    WebClientConfiguration webClient;

    {
        module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        xmlMapper = new XmlMapper(module);
    }

    @GetMapping
    public ResponseEntity<String> find(@RequestParam(value = "q", required = false) List<String> q,
                                       @RequestParam(value = "is_trading", required = false) String is_trading,
                                       @RequestParam(value = "start", required = false) String startFrom) {
        try {
            String response = webClient.webClientWithTimeout().get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/iss/securities.xml")
                            .queryParam("q", q)
                            .queryParam("is_trading", is_trading)
                            .queryParam("start", startFrom)
                            .queryParam("iss.meta", "off")
                            .queryParam("limit", 100)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofMillis(10000))
                    .block();

            Document value = xmlMapper.readValue(response, Document.class);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(value);
            if (json.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(json, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}