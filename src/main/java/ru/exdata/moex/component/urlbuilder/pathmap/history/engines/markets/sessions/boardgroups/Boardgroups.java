package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.sessions.boardgroups;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.sessions.Session;

public final class Boardgroups {
    final Session session;
    private final Query query;

    public Boardgroups(Session session, final Query query) {
        this.session = session;
        this.query = query;
    }

    public Boardgroup boardgroup(final String boardgroup) {
        if (boardgroup == null || boardgroup.isBlank()) {
            throw new IllegalArgumentException(boardgroup);
        }
        return new Boardgroup(this, this.query.addPath(boardgroup));
    }

}
