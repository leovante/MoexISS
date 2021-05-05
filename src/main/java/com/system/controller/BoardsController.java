package com.system.controller;

import com.configuration.WebClientConfiguration;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.UntypedObjectDeserializer;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.system.model.TQBR.Security_json;
import jline.internal.Nullable;
import org.json.JSONArray;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Duration;
import java.util.*;

@RestController
@RequestMapping("/api/TQBR/securities")
public class BoardsController {
    JacksonXmlModule module;
    XmlMapper xmlMapper;
    JsonMapper jsonMapper;

    @Autowired
    WebClientConfiguration webClient;

    {
        module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        xmlMapper = new XmlMapper(module);
        jsonMapper = new JsonMapper();
    }

    @GetMapping
    public ResponseEntity find(@RequestParam(value = "securities.columns", required = false) String securities_columns,
                               @RequestParam(value = "iss.only", required = false) String iss_only) {
        try {
            String response = webClient.webClientWithTimeout().get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/iss/engines/stock/markets/shares/boards/TQBR/securities.xml")
                            .queryParam("iss.meta", "off")
                            .queryParamIfPresent("iss.only", Optional.ofNullable(iss_only))
                            .queryParamIfPresent("securities.columns", Optional.ofNullable(securities_columns))
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
            return new ResponseEntity(soapDatainJsonObject.toString(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}