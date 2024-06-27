package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.stocks;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.Engines;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.stocks.totals.Totals;

public final class Stock {
    public final Engines engines;
    private final Query query;

    public Stock(Engines engines, final Query query) {
        this.engines = engines;
        this.query = query;
    }

    public Totals totals() {
        return new Totals(this, this.query.addPath("totals"));
    }

    public Markets markets() {
        return new Markets(this, this.query.addPath("markets"));
    }

    public Zcyc zcyc() {
        return new Zcyc(this, this.query.addPath("zcyc"));
    }

}
