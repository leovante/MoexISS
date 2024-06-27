package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.engine.markets;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Security {
    private final Securities securities;
    private final Query query;

    Security(Securities securities, final Query query) {
        this.securities = securities;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/775">Курсы переоценки коллатеральных инструментов. инструмент за интервал дат.</a>
     */
    public Format format() {
        return new Format(securities.market.markets.engine.engines.statistics.iss.issClient.httpClient, securities.market.markets.engine.engines.statistics.iss.issClient.uri, this.query);
    }
}
