package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.markets.bonds;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.markets.Markets;

public final class Bonds {
    final Markets markets;
    private final Query query;

    public Bonds(Markets markets, final Query query) {
        this.markets = markets;
        this.query = query;
    }

    public Aggregates aggregates() {
        return new Aggregates(this, this.query.addPath("aggregates"));
    }

}
