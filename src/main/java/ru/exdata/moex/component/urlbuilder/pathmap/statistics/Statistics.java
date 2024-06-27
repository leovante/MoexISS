package ru.exdata.moex.component.urlbuilder.pathmap.statistics;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.Iss;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.Engines;

public final class Statistics {
    public final Iss iss;
    private final Query query;

    public Statistics(Iss iss, final Query query) {
        this.iss = iss;
        this.query = query;
    }

    public Engines engines() {
        return new Engines(this, this.query.addPath("engines"));
    }

}
