package ru.exdata.moex.component.urlbuilder.pathmap.history.engines;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.history.History;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.stocks.Stock;

public final class Engines {
    public final History history;
    private final Query query;

    public Engines(History history, final Query query) {
        this.history = history;
        this.query = query;
    }

    public Engine engine(final String engine) {
        if (engine == null || engine.isBlank()) {
            throw new IllegalArgumentException(engine);
        }
        return new Engine(this, this.query.addPath(engine));
    }

    public Stock stock() {
        return new Stock(this, this.query.addPath("stock"));
    }

}
