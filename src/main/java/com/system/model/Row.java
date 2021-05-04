package com.system.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "row")
public class Row {
    @JacksonXmlProperty(isAttribute = true)
    public String id,
            secid,
            shortname,
            name,
            emitent_id,
            emitent_title,
            emitent_inn;

    public Row(@JsonProperty("id") String id,
               @JsonProperty("secid") String secid,
               @JsonProperty("shortname") String shortname,
               @JsonProperty("name") String name,
               @JsonProperty("emitent_id") String emitent_id,
               @JsonProperty("emitent_title") String emitent_title,
               @JsonProperty("emitent_inn") String emitent_inn) {
        this.id = id;
        this.secid = secid;
        this.shortname = shortname;
        this.name = name;
        this.emitent_id = emitent_id;
        this.emitent_title = emitent_title;
        this.emitent_inn = emitent_inn;
    }

    @Override
    public String toString() {
        return "Row{" +
                "id='" + id + '\'' +
                ", secid='" + secid + '\'' +
                ", shortname='" + shortname + '\'' +
                ", name='" + name + '\'' +
                ", emitent_id='" + emitent_id + '\'' +
                ", emitent_title='" + emitent_title + '\'' +
                ", emitent_inn='" + emitent_inn + '\'' +
                '}';
    }
}
