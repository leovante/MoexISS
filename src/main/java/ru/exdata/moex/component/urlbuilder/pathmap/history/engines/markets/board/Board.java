package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.board;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.board.securities.Securities;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.board.yields.Yields;

public final class Board {
    public final Boards boards;
    private final Query query;

    Board(Boards boards, final Query query) {
        this.boards = boards;
        this.query = query;
    }

    public Securities securities() {
        return new Securities(this, this.query.addPath("securities"));
    }

    public Dates dates() {
        return new Dates(this, this.query.addPath("dates"));
    }

    public Listing listing() {
        return new Listing(this, this.query.addPath("listing"));
    }

    public Yields yields() {
        return new Yields(this, this.query.addPath("yields"));
    }

}
