package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.session;

import ru.exdata.moex.component.urlbuilder.Query;

public final class Boardgroup {
    final Boardgroups boardgroups;
    private final Query query;

    Boardgroup(Boardgroups boardgroups, final Query query) {
        this.boardgroups = boardgroups;
        this.query = query;
    }

    public Securities securities() {
        return new Securities(this, this.query.addPath("securities"));
    }

}
