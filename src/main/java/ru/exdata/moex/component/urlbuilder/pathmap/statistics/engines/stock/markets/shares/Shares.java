package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.markets.shares;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.markets.Markets;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.markets.shares.Correlations;

public final class Shares {
    final Markets markets;
    private final Query query;

    public Shares(Markets markets, final Query query) {
        this.markets = markets;
        this.query = query;
    }

    public Correlations correlations() {
        return new Correlations(this, this.query.addPath("correlations"));
    }

}
