package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.futures;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Security {
    private final Securities securities;
    private final Query query;

    Security(Securities securities, final Query query) {
        this.securities = securities;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/712">Индикативный курс валют срочного рынка</a>
     */
    public Format format() {
        return new Format(securities.indicativerates.markets.futures.engines.statistics.iss.issClient.httpClient, securities.indicativerates.markets.futures.engines.statistics.iss.issClient.uri, this.query);
    }
}
