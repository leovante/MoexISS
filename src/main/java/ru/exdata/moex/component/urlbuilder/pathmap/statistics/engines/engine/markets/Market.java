package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.engine.markets;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Market {
    final Markets markets;
    private final Query query;

    Market(Markets markets, final Query query) {
        this.markets = markets;
        this.query = query;
    }

    public Securities securities() {
        return new Securities(this, this.query.addPath("securities"));
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/771">Курсы переоценки коллатеральных инструментов</a>
     */
    public Format format() {
        return new Format(markets.engine.engines.statistics.iss.issClient.httpClient, markets.engine.engines.statistics.iss.issClient.uri, this.query);
    }

}
