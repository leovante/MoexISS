package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Currentprices {
    private final Stock stock;
    private final Query query;

    Currentprices(Stock stock, final Query query) {
        this.stock = stock;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/649">Текущие цены бумаг</a>
     */
    public Format format() {
        return new Format(stock.engines.statistics.iss.issClient.httpClient, stock.engines.statistics.iss.issClient.uri, this.query);
    }
}
