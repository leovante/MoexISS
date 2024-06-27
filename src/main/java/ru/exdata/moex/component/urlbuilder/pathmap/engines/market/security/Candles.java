package ru.exdata.moex.component.urlbuilder.pathmap.engines.market.security;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Candles {
    private final Security security;
    private final Query query;

    Candles(Security security, final Query query) {
        this.security = security;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/155">Получить свечи указанного инструмента по дефолтной группе режимов.</a>
     */
    public Format format() {
        return new Format(security.securities.market.markets.engine.engines.iss.issClient.httpClient, security.securities.market.markets.engine.engines.iss.issClient.uri, this.query);
    }
}
