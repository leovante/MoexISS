package com.system.model.TQBR;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Security_json {

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

    public Security_json() {
    }

    public Security_json(String SECID,
                         String BOARDID,
                         String PREVPRICE,
                         String STATUS,
                         String BOARDNAME,
                         String PREVWAPRICE,
                         String PREVDATE,
                         String PREVLEGALCLOSEPRICE,
                         String PREVADMITTEDQUOTE,
                         String LISTLEVEL) {
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
