package ru.exdata.moex.component.urlbuilder.pathmap.engines.market.board.security;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.engines.market.board.Securities;

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

    public Candleborders candleborders() {
        return new Candleborders(this, this.query.addPath("candleborders"));
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/53">Получить данные по указанному инструменту на выбранном режиме торгов.</a>
     */
    public Format format() {
        return new Format(securities.board.boards.market.markets.engine.engines.iss.issClient.httpClient, securities.board.boards.market.markets.engine.engines.iss.issClient.uri, this.query);
    }

    public Candles candles() {
        return new Candles(this, this.query.addPath("candles"));
    }

    public Trades trades() {
        return new Trades(this, this.query.addPath("trades"));
    }

}
