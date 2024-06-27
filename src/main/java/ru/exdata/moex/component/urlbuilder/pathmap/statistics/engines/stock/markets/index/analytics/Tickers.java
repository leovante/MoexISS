package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.markets.index.analytics;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Tickers {
    final Indexid indexid;
    private final Query query;

    Tickers(Indexid indexid, final Query query) {
        this.indexid = indexid;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/148">Список тикеров за все время торгов</a>
     */
    public Format format() {
        return new Format(indexid.analytics.index.markets.stock.engines.statistics.iss.issClient.httpClient, indexid.analytics.index.markets.stock.engines.statistics.iss.issClient.uri, this.query);
    }

    public Ticker ticker(final String ticker) {
        if (ticker == null || ticker.isBlank()) {
            throw new IllegalArgumentException(ticker);
        }
        return new Ticker(this, this.query.addPath(ticker));
    }

}
