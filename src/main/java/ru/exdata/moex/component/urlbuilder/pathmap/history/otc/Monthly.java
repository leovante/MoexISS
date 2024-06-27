package ru.exdata.moex.component.urlbuilder.pathmap.history.otc;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.history.otc.Market;

public final class Monthly {
    private final Market market;
    private final Query query;

    Monthly(Market market, final Query query) {
        this.market = market;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/837">Ежедневные обобщенные данные отс пфи и репо.</a>
     */
    public Format format() {
        return new Format(market.markets.nsd.providers.otc.history.iss.issClient.httpClient, market.markets.nsd.providers.otc.history.iss.issClient.uri, this.query);
    }
}
