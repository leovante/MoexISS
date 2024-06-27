package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.state;

import ru.exdata.moex.component.urlbuilder.Query;

public final class Repo {
    final Markets markets;
    private final Query query;

    Repo(Markets markets, final Query query) {
        this.markets = markets;
        this.query = query;
    }

    public Cboper cboper() {
        return new Cboper(this, this.query.addPath("cboper"));
    }

    public Dealers dealers() {
        return new Dealers(this, this.query.addPath("dealers"));
    }

    public Mirp mirp() {
        return new Mirp(this, this.query.addPath("mirp"));
    }

}
