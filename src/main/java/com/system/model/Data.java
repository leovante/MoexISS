package com.system.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "data")
public class Data {

    @JacksonXmlProperty(localName = "rows")
    public Rows rows;

    @JacksonXmlProperty(isAttribute = true)
    public String id;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Data(@JsonProperty("rows") Rows rows,
                @JsonProperty("id") String id) {
        this.rows = rows;
    }
}
