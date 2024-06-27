package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.session;

import ru.exdata.moex.component.urlbuilder.Query;

public final class Boardgroups {
    final Session_ session;
    private final Query query;

    Boardgroups(Session_ session, final Query query) {
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
