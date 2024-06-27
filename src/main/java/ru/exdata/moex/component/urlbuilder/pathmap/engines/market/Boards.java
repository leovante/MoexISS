package ru.exdata.moex.component.urlbuilder.pathmap.engines.market;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.engines.market.board.Board;

public final class Boards {
    public final Market market;
    private final Query query;

    Boards(Market market, final Query query) {
        this.market = market;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/43">Получить справочник режимов торгов рынка. например: https://iss.moex.com/iss/engines/stock/markets/shares/boards.xml</a>
     */
    public Format format() {
        return new Format(market.markets.engine.engines.iss.issClient.httpClient, market.markets.engine.engines.iss.issClient.uri, this.query);
    }

    public Board board(final String board) {
        if (board == null || board.isBlank()) {
            throw new IllegalArgumentException(board);
        }
        return new Board(this, this.query.addPath(board));
    }

}
