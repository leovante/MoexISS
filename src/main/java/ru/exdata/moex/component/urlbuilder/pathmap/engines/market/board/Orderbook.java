package ru.exdata.moex.component.urlbuilder.pathmap.engines.market.board;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Orderbook {
    private final Board board;
    private final Query query;

    Orderbook(Board board, final Query query) {
        this.board = board;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/39">Получить все лучшие котировки по выбранному режиму торгов.</a>
     */
    public Format format() {
        return new Format(board.boards.market.markets.engine.engines.iss.issClient.httpClient, board.boards.market.markets.engine.engines.iss.issClient.uri, this.query);
    }
}
