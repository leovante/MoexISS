package ru.exdata.moex.component.urlbuilder.pathmap.engines.market.boardgroup.security;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Candles {
    private final Security security;
    private final Query query;

    Candles(Security security, final Query query) {
        this.security = security;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/157">Получить свечи указанного инструмента по выбранной группе режимов торгов.</a>
     */
    public Format format() {
        return new Format(security.securities.boardgroup.boardgroups.market.markets.engine.engines.iss.issClient.httpClient, security.securities.boardgroup.boardgroups.market.markets.engine.engines.iss.issClient.uri, this.query);
    }
}
