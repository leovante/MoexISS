package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.currency;

import ru.exdata.moex.component.urlbuilder.Query;

public final class Markets {
    final Currency currency;
    private final Query query;

    Markets(Currency currency, final Query query) {
        this.currency = currency;
        this.query = query;
    }

    public Selt selt() {
        return new Selt(this, this.query.addPath("selt"));
    }

    public Fixing fixing() {
        return new Fixing(this, this.query.addPath("fixing"));
    }

}
