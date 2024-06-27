package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.stocks;

import ru.exdata.moex.component.urlbuilder.Query;

public final class Shares {
    final Markets markets;
    private final Query query;

    Shares(Markets markets, final Query query) {
        this.markets = markets;
        this.query = query;
    }

    public Securities securities() {
        return new Securities(this, this.query.addPath("securities"));
    }

}
