package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.boardgroups;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.boardgroups.dates.Dates;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.boardgroups.securities.Securities;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.boardgroups.yields.Yields;

public final class Boardgroup {
    public final Boardgroups boardgroups;
    private final Query query;

    Boardgroup(Boardgroups boardgroups, final Query query) {
        this.boardgroups = boardgroups;
        this.query = query;
    }

    public Yields yields() {
        return new Yields(this, this.query.addPath("yields"));
    }

    public Dates dates() {
        return new Dates(this, this.query.addPath("dates"));
    }

    public Listing listing() {
        return new Listing(this, this.query.addPath("listing"));
    }

    public Securities securities() {
        return new Securities(this, this.query.addPath("securities"));
    }

}
