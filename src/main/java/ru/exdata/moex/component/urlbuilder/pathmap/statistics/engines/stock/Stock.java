package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.Engines;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.markets.Markets;

public final class Stock {
    public final Engines engines;
    private final Query query;

    public Stock(Engines engines, final Query query) {
        this.engines = engines;
        this.query = query;
    }

    public Deviationcoeffs deviationcoeffs() {
        return new Deviationcoeffs(this, this.query.addPath("deviationcoeffs"));
    }

    public Currentprices currentprices() {
        return new Currentprices(this, this.query.addPath("currentprices"));
    }

    public Markets markets() {
        return new Markets(this, this.query.addPath("markets"));
    }

    public Capitalization capitalization() {
        return new Capitalization(this, this.query.addPath("capitalization"));
    }

    public Quotedsecurities quotedsecurities() {
        return new Quotedsecurities(this, this.query.addPath("quotedsecurities"));
    }

    public Splits splits() {
        return new Splits(this, this.query.addPath("splits"));
    }

    public Securitieslisting securitieslisting() {
        return new Securitieslisting(this, this.query.addPath("securitieslisting"));
    }

}
