package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.sessions.board;

import ru.exdata.moex.component.urlbuilder.Query;

public final class Board {
    final Boards boards;
    private final Query query;

    Board(Boards boards, final Query query) {
        this.boards = boards;
        this.query = query;
    }

    public Securities securities() {
        return new Securities(this, this.query.addPath("securities"));
    }

}
