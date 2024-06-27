package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.markets;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.Stock;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.markets.bonds.Bonds;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.markets.index.Index;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.markets.shares.Shares;

public final class Markets {
    public final Stock stock;
    private final Query query;

    public Markets(Stock stock, final Query query) {
        this.stock = stock;
        this.query = query;
    }

    public Shares shares() {
        return new Shares(this, this.query.addPath("shares"));
    }

    public Bonds bonds() {
        return new Bonds(this, this.query.addPath("bonds"));
    }

    public Index index() {
        return new Index(this, this.query.addPath("index"));
    }

}
