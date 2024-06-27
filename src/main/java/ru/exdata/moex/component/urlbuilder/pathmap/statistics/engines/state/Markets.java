package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.state;

import ru.exdata.moex.component.urlbuilder.Query;

public final class Markets {
    final State state;
    private final Query query;

    Markets(State state, final Query query) {
        this.state = state;
        this.query = query;
    }

    public Repo repo() {
        return new Repo(this, this.query.addPath("repo"));
    }

}
