package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.securities;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.Market;

public final class Securities {
    final Market market;
    private final Query query;

    public Securities(Market market, final Query query) {
        this.market = market;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/62">Получить историю по всем бумагам на рынке за одну дату.
     * например: https://iss.moex.com/iss/history/engines/stock/markets/index/securities.xml?date=2010-11-22</a>
     */
    public Format format() {
        return new Format(market.markets.engine.engines.history.iss.issClient.httpClient, market.markets.engine.engines.history.iss.issClient.uri, this.query);
    }

    public Security security(final String security) {
        if (security == null || security.isBlank()) {
            throw new IllegalArgumentException(security);
        }
        return new Security(this, this.query.addPath(security));
    }

}
