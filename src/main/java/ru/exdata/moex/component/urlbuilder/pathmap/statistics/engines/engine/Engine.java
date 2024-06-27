package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.engine;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.Engines;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.engine.markets.Markets;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.engine.derivatives.Derivatives;

public final class Engine {
    public final Engines engines;
    private final Query query;

    public Engine(Engines engines, final Query query) {
        this.engines = engines;
        this.query = query;
    }

    public Monthly monthly() {
        return new Monthly(this, this.query.addPath("monthly"));
    }

    public Markets markets() {
        return new Markets(this, this.query.addPath("markets"));
    }

    public Derivatives derivatives() {
        return new Derivatives(this, this.query.addPath("derivatives"));
    }

}
