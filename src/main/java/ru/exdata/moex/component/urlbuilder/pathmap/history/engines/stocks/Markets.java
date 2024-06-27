package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.stocks;

import ru.exdata.moex.component.urlbuilder.Query;

public final class Markets {
    final Stock stock;
    private final Query query;

    Markets(Stock stock, final Query query) {
        this.stock = stock;
        this.query = query;
    }

    public Shares shares() {
        return new Shares(this, this.query.addPath("shares"));
    }

}
