package ru.exdata.moex.component.urlbuilder.pathmap.engines.market.security;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Trades {
    private final Security security;
    private final Query query;

    Trades(Security security, final Query query) {
        this.security = security;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/55">Получить сделки по инструменту. например: https://iss.moex.com/iss/engines/stock/markets/shares/securities/aflt/trades.xml</a>
     */
    public Format format() {
        return new Format(security.securities.market.markets.engine.engines.iss.issClient.httpClient, security.securities.market.markets.engine.engines.iss.issClient.uri, this.query);
    }
}
