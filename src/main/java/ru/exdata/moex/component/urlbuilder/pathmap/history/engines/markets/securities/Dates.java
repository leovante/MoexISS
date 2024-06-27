package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.securities;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Dates {
    private final Security security;
    private final Query query;

    Dates(Security security, final Query query) {
        this.security = security;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/61">Получить интервал дат в истории для указанного рынка и бумаги.</a>
     */
    public Format format() {
        return new Format(security.securities.market.markets.engine.engines.history.iss.issClient.httpClient, security.securities.market.markets.engine.engines.history.iss.issClient.uri, this.query);
    }
}
