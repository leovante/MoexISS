package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.stocks;

import ru.exdata.moex.component.urlbuilder.Query;

public final class Securities {
    final Shares shares;
    private final Query query;

    Securities(Shares shares, final Query query) {
        this.shares = shares;
        this.query = query;
    }

    public Changeover changeover() {
        return new Changeover(this, this.query.addPath("changeover"));
    }

}
