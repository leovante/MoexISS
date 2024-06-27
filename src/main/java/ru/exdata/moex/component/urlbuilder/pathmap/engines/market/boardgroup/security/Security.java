package ru.exdata.moex.component.urlbuilder.pathmap.engines.market.boardgroup.security;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.engines.market.boardgroup.Candleborders;
import ru.exdata.moex.component.urlbuilder.pathmap.engines.market.boardgroup.Securities;

public final class Security {
    public final Securities securities;
    private final Query query;

    public Security(Securities securities, final Query query) {
        this.securities = securities;
        this.query = query;
    }

    public Candleborders candleborders() {
        return new Candleborders(this, this.query.addPath("candleborders"));
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/58">Получить данные по указанному инструменту, торгуемому на выбранной группе режимов торгов.</a>
     */
    public Format format() {
        return new Format(securities.boardgroup.boardgroups.market.markets.engine.engines.iss.issClient.httpClient, securities.boardgroup.boardgroups.market.markets.engine.engines.iss.issClient.uri, this.query);
    }

    public Trades trades() {
        return new Trades(this, this.query.addPath("trades"));
    }

    public Candles candles() {
        return new Candles(this, this.query.addPath("candles"));
    }

    public Orderbook orderbook() {
        return new Orderbook(this, this.query.addPath("orderbook"));
    }

}
