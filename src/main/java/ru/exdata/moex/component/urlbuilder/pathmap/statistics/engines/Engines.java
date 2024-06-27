package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.*;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.currency.Currency;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.engine.Engine;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.futures.Futures;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.state.State;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.Stock;

public final class Engines {
    public final Statistics statistics;
    private final Query query;

    public Engines(Statistics statistics, final Query query) {
        this.statistics = statistics;
        this.query = query;
    }

    public Currency currency() {
        return new Currency(this, this.query.addPath("currency"));
    }

    public Futures futures() {
        return new Futures(this, this.query.addPath("futures"));
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

    public State state() {
        return new State(this, this.query.addPath("state"));
    }

}
