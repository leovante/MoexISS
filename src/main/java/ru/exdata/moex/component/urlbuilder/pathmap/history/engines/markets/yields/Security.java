package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.yields;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Security {
    private final Yields yields;
    private final Query query;

    Security(Yields yields, final Query query) {
        this.yields = yields;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/793">Получить историю доходностей по одной бумаге на рынке за интервал дат.</a>
     */
    public Format format() {
        return new Format(yields.market.markets.engine.engines.history.iss.issClient.httpClient, yields.market.markets.engine.engines.history.iss.issClient.uri, this.query);
    }
}
