package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.markets.index;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.markets.Markets;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.markets.index.analytics.Analytics;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.markets.index.bullectins.Bulletins;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.markets.index.rusfar.Rusfar;

public final class Index {
    public final Markets markets;
    private final Query query;

    public Index(Markets markets, final Query query) {
        this.markets = markets;
        this.query = query;
    }

    public Rusfar rusfar() {
        return new Rusfar(this, this.query.addPath("rusfar"));
    }

    public Bulletins bulletins() {
        return new Bulletins(this, this.query.addPath("bulletins"));
    }

    public Analytics analytics() {
        return new Analytics(this, this.query.addPath("analytics"));
    }

}
