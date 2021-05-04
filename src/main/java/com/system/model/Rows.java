package com.system.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "rows")
public class Rows {

    @JacksonXmlProperty(localName = "row")
    public List<Row> rows;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Rows(@JsonProperty("row") List<Row> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "Data{" +
                "rows=" + rows +
                '}';
    }
}
