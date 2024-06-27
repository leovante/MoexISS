package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.board;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.Market;

public final class Boards {
    public final Market market;
    private final Query query;

    public Boards(Market market, final Query query) {
        this.market = market;
        this.query = query;
    }

    public Board board(final String board) {
        if (board == null || board.isBlank()) {
            throw new IllegalArgumentException(board);
        }
        return new Board(this, this.query.addPath(board));
    }

}
