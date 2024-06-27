package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.futures;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.Engines;

public final class Futures {
    final Engines engines;
    private final Query query;

    public Futures(Engines engines, final Query query) {
        this.engines = engines;
        this.query = query;
    }

    public Markets markets() {
        return new Markets(this, this.query.addPath("markets"));
    }

}
