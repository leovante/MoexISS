package com.system.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "document")
public class Document {

    public Data data;

    public Document(@JsonProperty("rows") Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Document{" +
                "rows=" + data +
                '}';
    }
}
