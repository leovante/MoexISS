package ru.exdata.moex.component.urlbuilder.pathmap.rms;

import ru.exdata.moex.component.urlbuilder.Query;

public final class Engines {
    final Rms rms;
    private final Query query;

    Engines(Rms rms, final Query query) {
        this.rms = rms;
        this.query = query;
    }

    public Engine engine(final String engine) {
        if (engine == null || engine.isBlank()) {
            throw new IllegalArgumentException(engine);
        }
        return new Engine(this, this.query.addPath(engine));
    }

}
