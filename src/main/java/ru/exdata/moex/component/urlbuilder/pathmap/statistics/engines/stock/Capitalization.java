package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Capitalization {
    private final Stock stock;
    private final Query query;

    Capitalization(Stock stock, final Query query) {
        this.stock = stock;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/159">Капитализация фондового рынка</a>
     */
    public Format format() {
        return new Format(stock.engines.statistics.iss.issClient.httpClient, stock.engines.statistics.iss.issClient.uri, this.query);
    }
}
