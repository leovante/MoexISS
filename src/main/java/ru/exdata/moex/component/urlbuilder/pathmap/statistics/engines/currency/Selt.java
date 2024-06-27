package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.currency;

import ru.exdata.moex.component.urlbuilder.Query;

public final class Selt {
    final Markets markets;
    private final Query query;

    Selt(Markets markets, final Query query) {
        this.markets = markets;
        this.query = query;
    }

    public Rates rates() {
        return new Rates(this, this.query.addPath("rates"));
    }

}
