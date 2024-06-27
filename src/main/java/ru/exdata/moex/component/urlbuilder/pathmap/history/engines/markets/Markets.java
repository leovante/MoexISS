package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.Engine;

public final class Markets {
    public final Engine engine;
    private final Query query;

    public Markets(Engine engine, final Query query) {
        this.engine = engine;
        this.query = query;
    }

    public Market market(final String market) {
        if (market == null || market.isBlank()) {
            throw new IllegalArgumentException(market);
        }
        return new Market(this, this.query.addPath(market));
    }

}
