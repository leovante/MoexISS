package ru.exdata.moex.component.urlbuilder.pathmap.engines.market;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Orderbook {
    private final Market market;
    private final Query query;

    Orderbook(Market market, final Query query) {
        this.market = market;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/36">Получить стаканы заявок всех инструментов рынка. например: https://iss.moex.com/iss/engines/stock/markets/shares/orderbook.xml</a>
     */
    public Format format() {
        return new Format(market.markets.engine.engines.iss.issClient.httpClient, market.markets.engine.engines.iss.issClient.uri, this.query);
    }
}
