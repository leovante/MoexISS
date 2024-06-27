package ru.exdata.moex.component.urlbuilder.pathmap.engines.market;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Trades {
    private final Market market;
    private final Query query;

    Trades(Market market, final Query query) {
        this.market = market;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/35">Получить все сделки рынка. например: https://iss.moex.com/iss/engines/stock/markets/shares/trades.xml</a>
     */
    public Format format() {
        return new Format(market.markets.engine.engines.iss.issClient.httpClient, market.markets.engine.engines.iss.issClient.uri, this.query);
    }
}
