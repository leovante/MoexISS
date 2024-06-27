package ru.exdata.moex.component.urlbuilder.pathmap.engines.market.boardgroup;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.engines.market.Boardgroups;

public final class Boardgroup {
    public final Boardgroups boardgroups;
    private final Query query;

    public Boardgroup(Boardgroups boardgroups, final Query query) {
        this.boardgroups = boardgroups;
        this.query = query;
    }

    public Trades trades() {
        return new Trades(this, this.query.addPath("trades"));
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/50">Получить описание группы режимов торгов.</a>
     */
    public Format format() {
        return new Format(boardgroups.market.markets.engine.engines.iss.issClient.httpClient, boardgroups.market.markets.engine.engines.iss.issClient.uri, this.query);
    }

    public Securities securities() {
        return new Securities(this, this.query.addPath("securities"));
    }

    public Orderbook orderbook() {
        return new Orderbook(this, this.query.addPath("orderbook"));
    }

}
