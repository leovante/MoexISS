package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.futures;

import ru.exdata.moex.component.urlbuilder.Query;

public final class Indicativerates {
    final Markets markets;
    private final Query query;

    Indicativerates(Markets markets, final Query query) {
        this.markets = markets;
        this.query = query;
    }

    public Securities securities() {
        return new Securities(this, this.query.addPath("securities"));
    }

}
