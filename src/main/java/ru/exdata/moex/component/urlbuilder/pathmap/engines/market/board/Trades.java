package ru.exdata.moex.component.urlbuilder.pathmap.engines.market.board;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Trades {
    private final Board board;
    private final Query query;

    Trades(Board board, final Query query) {
        this.board = board;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/34">Получить все сделки по выбранному режиму торгов.</a>
     */
    public Format format() {
        return new Format(board.boards.market.markets.engine.engines.iss.issClient.httpClient, board.boards.market.markets.engine.engines.iss.issClient.uri, this.query);
    }
}
