package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.futures;

import ru.exdata.moex.component.urlbuilder.Query;

public final class Markets {
    final Futures futures;
    private final Query query;

    Markets(Futures futures, final Query query) {
        this.futures = futures;
        this.query = query;
    }

    public Options options() {
        return new Options(this, this.query.addPath("options"));
    }

    public Indicativerates indicativerates() {
        return new Indicativerates(this, this.query.addPath("indicativerates"));
    }

}
