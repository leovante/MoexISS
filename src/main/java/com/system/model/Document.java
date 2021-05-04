package com.system.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "document")
public class Document {

    @JacksonXmlProperty(localName = "data")
    public Data data;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Document(@JsonProperty("data") Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Document{" +
                "rows=" + data +
                '}';
    }
}
