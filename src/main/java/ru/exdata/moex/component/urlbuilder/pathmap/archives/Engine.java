package ru.exdata.moex.component.urlbuilder.pathmap.archives;

import ru.exdata.moex.component.urlbuilder.Query;

public final class Engine {
    final Engines engines;
    private final Query query;

    Engine(Engines engines, final Query query) {
        this.engines = engines;
        this.query = query;
    }

    public Markets markets() {
        return new Markets(this, this.query.addPath("markets"));
    }

}
