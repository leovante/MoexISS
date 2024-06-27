package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.stocks.totals.securities;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.stocks.totals.Totals;

public final class Securities {
    private final Totals totals;
    private final Query query;

    public Securities(Totals totals, final Query query) {
        this.totals = totals;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/162">Обобщенная информация по фондовому рынку</a>
     */
    public Format format() {
        return new Format(totals.stock.engines.history.iss.issClient.httpClient, totals.stock.engines.history.iss.issClient.uri, this.query);
    }
}
