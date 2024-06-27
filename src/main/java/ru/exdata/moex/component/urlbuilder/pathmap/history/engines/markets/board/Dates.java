package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.board;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Dates {
    private final Board board;
    private final Query query;

    Dates(Board board, final Query query) {
        this.board = board;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/26">Получить интервал дат, доступных в истории для рынка по заданному режиму торгов.</a>
     */
    public Format format() {
        return new Format(board.boards.market.markets.engine.engines.history.iss.issClient.httpClient, board.boards.market.markets.engine.engines.history.iss.issClient.uri, this.query);
    }
}
