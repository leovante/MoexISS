package com.system.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "data")
public class Data {

    public Rows rows;

    @JacksonXmlProperty(isAttribute = true)
    public String id;

    public Data(@JsonProperty("rows") Rows rows,
                @JsonProperty("id") String id) {
        this.rows = rows;
        this.id = id;
    }
}
