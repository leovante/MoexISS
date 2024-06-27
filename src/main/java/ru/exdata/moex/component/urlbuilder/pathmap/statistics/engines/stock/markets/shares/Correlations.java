package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.markets.shares;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Correlations {
    private final Shares shares;
    private final Query query;

    Correlations(Shares shares, final Query query) {
        this.shares = shares;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/172">Коэффициенты корелляции фондового рынка</a>
     */
    public Format format() {
        return new Format(shares.markets.stock.engines.statistics.iss.issClient.httpClient, shares.markets.stock.engines.statistics.iss.issClient.uri, this.query);
    }
}
