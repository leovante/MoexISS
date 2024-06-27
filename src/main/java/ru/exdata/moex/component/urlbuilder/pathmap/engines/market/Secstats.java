package ru.exdata.moex.component.urlbuilder.pathmap.engines.market;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Secstats {
    private final Market market;
    private final Query query;

    Secstats(Market market, final Query query) {
        this.market = market;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/823">Промежуточные "итоги дня". только для фондового рынка</a>
     */
    public Format format() {
        return new Format(market.markets.engine.engines.iss.issClient.httpClient, market.markets.engine.engines.iss.issClient.uri, this.query);
    }
}
