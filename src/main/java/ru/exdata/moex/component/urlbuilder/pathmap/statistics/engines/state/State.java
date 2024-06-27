package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.state;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.Engines;

public final class State {
    final Engines engines;
    private final Query query;

    public State(Engines engines, final Query query) {
        this.engines = engines;
        this.query = query;
    }

    public Rates rates() {
        return new Rates(this, this.query.addPath("rates"));
    }

    public Markets markets() {
        return new Markets(this, this.query.addPath("markets"));
    }

}
