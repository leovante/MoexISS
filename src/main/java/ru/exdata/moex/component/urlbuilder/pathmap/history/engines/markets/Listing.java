package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Listing {
    private final Market market;
    private final Query query;

    Listing(Market market, final Query query) {
        this.market = market;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/118">Список неторгуемых инструментов с указанием интервалов торгуемости по режимам</a>
     */
    public Format format() {
        return new Format(market.markets.engine.engines.history.iss.issClient.httpClient, market.markets.engine.engines.history.iss.issClient.uri, this.query);
    }
}
