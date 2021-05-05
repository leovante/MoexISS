package com.system.controller;

import com.configuration.WebClientConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.system.model.Document;
import org.json.JSONArray;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Optional;

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
    public ResponseEntity<String> find(@RequestParam(value = "q", required = false) String q,
                                       @RequestParam(value = "is_trading", required = false) String is_trading,
                                       @RequestParam(value = "start", required = false) String startFrom,
                                       @RequestParam(value = "limit", required = false) int limit) {
        try {
            String response = webClient.webClientWithTimeout().get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/iss/securities.xml")
                            .queryParam("iss.meta", "off")
                            .queryParamIfPresent("q", Optional.ofNullable(q))
                            .queryParamIfPresent("is_trading", Optional.ofNullable(is_trading))
                            .queryParamIfPresent("start", Optional.ofNullable(startFrom))
                            .queryParamIfPresent("limit", Optional.ofNullable(limit))
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofMillis(10000))
                    .block();

            JSONArray soapDatainJsonObject = XML.toJSONObject(response)
                    .getJSONObject("document")
                    .getJSONObject("data")
                    .getJSONObject("rows")
                    .getJSONArray("row");

//            ObjectMapper OBJECT_MAPPER = new ObjectMapper();
//            List<Security_json> listJson = OBJECT_MAPPER.readValue(soapDatainJsonObject.toString(), new TypeReference<List<Security_json>>() {
//            });

            if (soapDatainJsonObject.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(soapDatainJsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}