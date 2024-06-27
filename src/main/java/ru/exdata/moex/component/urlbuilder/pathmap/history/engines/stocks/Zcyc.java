package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.stocks;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.stocks.Stock;

public final class Zcyc {
    private final Stock stock;
    private final Query query;

    Zcyc(Stock stock, final Query query) {
        this.stock = stock;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/783">История изменения параметров кбд (кривая бескупоной доходности).</a>
     */
    public Format format() {
        return new Format(stock.engines.history.iss.issClient.httpClient, stock.engines.history.iss.issClient.uri, this.query);
    }
}
