package ru.exdata.moex.component.urlbuilder.pathmap.history.engines;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.Markets;

public final class Engine {
    public final Engines engines;
    private final Query query;

    Engine(Engines engines, final Query query) {
        this.engines = engines;
        this.query = query;
    }

    public Markets markets() {
        return new Markets(this, this.query.addPath("markets"));
    }

}
