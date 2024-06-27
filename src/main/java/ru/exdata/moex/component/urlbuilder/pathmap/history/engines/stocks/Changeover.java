package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.stocks;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Changeover {
    private final Securities securities;
    private final Query query;

    Changeover(Securities securities, final Query query) {
        this.securities = securities;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/123">Информация по техническому изменению торговых кодов</a>
     */
    public Format format() {
        return new Format(securities.shares.markets.stock.engines.history.iss.issClient.httpClient, securities.shares.markets.stock.engines.history.iss.issClient.uri, this.query);
    }
}
