package ru.exdata.moex.component.urlbuilder.pathmap.history.otc;

import ru.exdata.moex.component.urlbuilder.Query;

public final class Market {
    final Markets markets;
    private final Query query;

    Market(Markets markets, final Query query) {
        this.markets = markets;
        this.query = query;
    }

    public Daily daily() {
        return new Daily(this, this.query.addPath("daily"));
    }

    public Monthly monthly() {
        return new Monthly(this, this.query.addPath("monthly"));
    }

}
