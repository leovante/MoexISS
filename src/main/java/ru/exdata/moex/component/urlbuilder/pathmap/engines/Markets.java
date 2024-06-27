package ru.exdata.moex.component.urlbuilder.pathmap.engines;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.engines.market.Market;

public final class Markets {
    public final Engine engine;
    private final Query query;

    Markets(Engine engine, final Query query) {
        this.engine = engine;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/42">Получить список рынков торговой системы. например: https://iss.moex.com/iss/engines/stock/markets.xml</a>
     */
    public Format format() {
        return new Format(engine.engines.iss.issClient.httpClient, engine.engines.iss.issClient.uri, this.query);
    }

    public Zcyc zcyc() {
        return new Zcyc(engine, this.query.addPath("zcyc"));
    }

    public Market market(final String market) {
        if (market == null || market.isBlank()) {
            throw new IllegalArgumentException(market);
        }
        return new Market(this, this.query.addPath(market));
    }

}
