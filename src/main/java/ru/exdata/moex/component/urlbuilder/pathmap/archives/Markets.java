package ru.exdata.moex.component.urlbuilder.pathmap.archives;

import ru.exdata.moex.component.urlbuilder.Query;

public final class Markets {
    final Engine engine;
    private final Query query;

    Markets(Engine engine, final Query query) {
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
