package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.markets.index.analytics;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Indexid {
    final Analytics analytics;
    private final Query query;

    Indexid(Analytics analytics, final Query query) {
        this.analytics = analytics;
        this.query = query;
    }

    public Tickers tickers() {
        return new Tickers(this, this.query.addPath("tickers"));
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/147">Аналитические показатели за дату</a>
     */
    public Format format() {
        return new Format(analytics.index.markets.stock.engines.statistics.iss.issClient.httpClient, analytics.index.markets.stock.engines.statistics.iss.issClient.uri, this.query);
    }

}
