package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.boardgroups.securities;

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
     * <a href="https://iss.moex.com/iss/reference/69">Получить интервал дат для указанной бумаги на заданной группе режимов торгов.</a>
     */
    public Format format() {
        return new Format(security.securities.boardgroup.boardgroups.market.markets.engine.engines.history.iss.issClient.httpClient, security.securities.boardgroup.boardgroups.market.markets.engine.engines.history.iss.issClient.uri, this.query);
    }
}
