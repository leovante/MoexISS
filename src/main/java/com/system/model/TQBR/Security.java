package com.system.model.TQBR;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "row")
public class Security {
    @JacksonXmlProperty(isAttribute = true)
    public String
            SECID,
            BOARDID,
            PREVPRICE,
            STATUS,
            BOARDNAME,
            PREVWAPRICE,
            PREVDATE,
            PREVLEGALCLOSEPRICE,
            PREVADMITTEDQUOTE,
            LISTLEVEL;

    public Security(@JsonProperty("SECID") String SECID,
                    @JsonProperty("BOARDID") String BOARDID,
                    @JsonProperty("PREVPRICE") String PREVPRICE,
                    @JsonProperty("STATUS") String STATUS,
                    @JsonProperty("BOARDNAME") String BOARDNAME,
                    @JsonProperty("PREVWAPRICE") String PREVWAPRICE,
                    @JsonProperty("PREVDATE") String PREVDATE,
                    @JsonProperty("PREVLEGALCLOSEPRICE") String PREVLEGALCLOSEPRICE,
                    @JsonProperty("PREVADMITTEDQUOTE") String PREVADMITTEDQUOTE,
                    @JsonProperty("LISTLEVEL") String LISTLEVEL) {
        this.SECID = SECID;
        this.BOARDID = BOARDID;
        this.PREVPRICE = PREVPRICE;
        this.STATUS = STATUS;
        this.BOARDNAME = BOARDNAME;
        this.PREVWAPRICE = PREVWAPRICE;
        this.PREVDATE = PREVDATE;
        this.PREVLEGALCLOSEPRICE = PREVLEGALCLOSEPRICE;
        this.PREVADMITTEDQUOTE = PREVADMITTEDQUOTE;
        this.LISTLEVEL = LISTLEVEL;
    }

    @Override
    public String toString() {
        return "Security{" +
                "SECID='" + SECID + '\'' +
                ", BOARDID='" + BOARDID + '\'' +
                ", PREVPRICE='" + PREVPRICE + '\'' +
                ", STATUS='" + STATUS + '\'' +
                ", BOARDNAME='" + BOARDNAME + '\'' +
                ", PREVWAPRICE='" + PREVWAPRICE + '\'' +
                ", PREVDATE='" + PREVDATE + '\'' +
                ", PREVLEGALCLOSEPRICE='" + PREVLEGALCLOSEPRICE + '\'' +
                ", PREVADMITTEDQUOTE='" + PREVADMITTEDQUOTE + '\'' +
                ", LISTLEVEL='" + LISTLEVEL + '\'' +
                '}';
    }
}
