package ru.exdata.moex.component.urlbuilder.pathmap.securities;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Security {
    final Securities securities;
    private final Query query;

    Security(Securities securities, final Query query) {
        this.securities = securities;
        this.query = query;
    }

    public Aggregates aggregates() {
        return new Aggregates(this, this.query.addPath("aggregates"));
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/13">Получить спецификацию инструмента. например: https://iss.moex.com/iss/securities/imoex.xml</a>
     */
    public Format format() {
        return new Format(securities.iss.issClient.httpClient, securities.iss.issClient.uri, this.query);
    }

    public Indices indices() {
        return new Indices(this, this.query.addPath("indices"));
    }

}
