package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.markets.index.analytics;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Ticker {
    private final Tickers tickers;
    private final Query query;

    Ticker(Tickers tickers, final Query query) {
        this.tickers = tickers;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/149">Информация по тикеру</a>
     */
    public Format format() {
        return new Format(tickers.indexid.analytics.index.markets.stock.engines.statistics.iss.issClient.httpClient, tickers.indexid.analytics.index.markets.stock.engines.statistics.iss.issClient.uri, this.query);
    }
}
