package ru.exdata.moex.component.urlbuilder.pathmap.engines.market.board;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.engines.market.Boards;

public final class Board {
    public final Boards boards;
    private final Query query;

    public Board(Boards boards, final Query query) {
        this.boards = boards;
        this.query = query;
    }

    public Orderbook orderbook() {
        return new Orderbook(this, this.query.addPath("orderbook"));
    }

    public Trades trades() {
        return new Trades(this, this.query.addPath("trades"));
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/49">Получить описание режима торгов. например: https://iss.moex.com/iss/engines/stock/markets/shares/boards/tqbr.xml</a>
     */
    public Format format() {
        return new Format(boards.market.markets.engine.engines.iss.issClient.httpClient, boards.market.markets.engine.engines.iss.issClient.uri, this.query);
    }

    public Securities securities() {
        return new Securities(this, this.query.addPath("securities"));
    }

}
