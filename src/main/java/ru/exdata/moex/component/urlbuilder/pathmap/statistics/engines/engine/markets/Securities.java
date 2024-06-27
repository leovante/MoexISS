package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.engine.markets;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Securities {
    final Market market;
    private final Query query;

    Securities(Market market, final Query query) {
        this.market = market;
        this.query = query;
    }

    public Security security(final String security) {
        if (security == null || security.isBlank()) {
            throw new IllegalArgumentException(security);
        }
        return new Security(this, this.query.addPath(security));
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/773">Курсы переоценки коллатеральных инструментов</a>
     */
    public Format format() {
        return new Format(market.markets.engine.engines.statistics.iss.issClient.httpClient, market.markets.engine.engines.statistics.iss.issClient.uri, this.query);
    }

}
