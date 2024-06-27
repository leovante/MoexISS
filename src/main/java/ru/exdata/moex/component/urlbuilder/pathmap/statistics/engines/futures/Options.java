package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.futures;

import ru.exdata.moex.component.urlbuilder.Query;

public final class Options {
    final Markets markets;
    private final Query query;

    Options(Markets markets, final Query query) {
        this.markets = markets;
        this.query = query;
    }

    public Assets assets() {
        return new Assets(this, this.query.addPath("assets"));
    }

}
