package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.board;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Listing {
    private final Board board;
    private final Query query;

    Listing(Board board, final Query query) {
        this.board = board;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/119">Получить данные по листингу бумаг в историческом разрезе по указанному режиму</a>
     */
    public Format format() {
        return new Format(board.boards.market.markets.engine.engines.history.iss.issClient.httpClient, board.boards.market.markets.engine.engines.history.iss.issClient.uri, this.query);
    }
}
