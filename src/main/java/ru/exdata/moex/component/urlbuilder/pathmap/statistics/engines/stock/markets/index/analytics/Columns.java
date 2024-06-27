package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.markets.index.analytics;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.markets.index.analytics.Analytics;

public final class Columns {
    private final Analytics analytics;
    private final Query query;

    Columns(Analytics analytics, final Query query) {
        this.analytics = analytics;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/205"></a>
     */
    public Format format() {
        return new Format(analytics.index.markets.stock.engines.statistics.iss.issClient.httpClient, analytics.index.markets.stock.engines.statistics.iss.issClient.uri, this.query);
    }
}
