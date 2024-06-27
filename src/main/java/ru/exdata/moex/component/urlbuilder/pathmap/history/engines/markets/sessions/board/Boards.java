package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.sessions.board;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.sessions.Session;

public final class Boards {
    final Session session;
    private final Query query;

    public Boards(Session session, final Query query) {
        this.session = session;
        this.query = query;
    }

    public Board board(final String board) {
        if (board == null || board.isBlank()) {
            throw new IllegalArgumentException(board);
        }
        return new Board(this, this.query.addPath(board));
    }

}
