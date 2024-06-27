package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.session;

import ru.exdata.moex.component.urlbuilder.Query;

public final class Session_ {
    final Session session;
    private final Query query;

    Session_(Session session, final Query query) {
        this.session = session;
        this.query = query;
    }

    public Boardgroups boardgroups() {
        return new Boardgroups(this, this.query.addPath("boardgroups"));
    }

}
