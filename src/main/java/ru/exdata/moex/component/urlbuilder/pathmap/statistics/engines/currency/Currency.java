package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.currency;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.Engines;

public final class Currency {
    final Engines engines;
    private final Query query;

    public Currency(Engines engines, final Query query) {
        this.engines = engines;
        this.query = query;
    }

    public Markets markets() {
        return new Markets(this, this.query.addPath("markets"));
    }

}
