package ru.exdata.moex.component.urlbuilder.pathmap.rms;

import ru.exdata.moex.component.urlbuilder.Query;

public final class Engine {
    final Engines engines;
    private final Query query;

    Engine(Engines engines, final Query query) {
        this.engines = engines;
        this.query = query;
    }

    public Objects objects() {
        return new Objects(this, this.query.addPath("objects"));
    }

}
