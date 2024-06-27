package ru.exdata.moex.component.urlbuilder.pathmap.engines.market.security;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.engines.market.Securities;

public final class Security {
    final Securities securities;
    private final Query query;

    public Security(Securities securities, final Query query) {
        this.securities = securities;
        this.query = query;
    }

    public Orderbook orderbook() {
        return new Orderbook(this, this.query.addPath("orderbook"));
    }

    public Candles candles() {
        return new Candles(this, this.query.addPath("candles"));
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/52">Получить данные по конкретному инструменту рынка. например: https://iss.moex.com/iss/engines/stock/markets/shares/securities/aflt.xml</a>
     */
    public Format format() {
        return new Format(securities.market.markets.engine.engines.iss.issClient.httpClient, securities.market.markets.engine.engines.iss.issClient.uri, this.query);
    }

    public Trades trades() {
        return new Trades(this, this.query.addPath("trades"));
    }

    public Candleborders candleborders() {
        return new Candleborders(this, this.query.addPath("candleborders"));
    }

}
