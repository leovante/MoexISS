package ru.exdata.moex.component.urlbuilder.pathmap.rms;

import ru.exdata.moex.component.urlbuilder.Query;

public final class Objects {
    final Engine engine;
    private final Query query;

    Objects(Engine engine, final Query query) {
        this.engine = engine;
        this.query = query;
    }

    public Irr irr() {
        return new Irr(this, this.query.addPath("irr"));
    }

}
