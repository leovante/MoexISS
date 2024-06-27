package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.stocks.totals;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.stocks.Stock;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.stocks.totals.boards.Boards;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.stocks.totals.securities.Securities;

public final class Totals {
    public final Stock stock;
    private final Query query;

    public Totals(Stock stock, final Query query) {
        this.stock = stock;
        this.query = query;
    }

    public Boards boards() {
        return new Boards(this, this.query.addPath("boards"));
    }

    public Securities securities() {
        return new Securities(this, this.query.addPath("securities"));
    }

}
