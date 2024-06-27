package ru.exdata.moex.component.urlbuilder.pathmap.archives;

import ru.exdata.moex.component.urlbuilder.Query;

public final class Engines {
    final Archives archives;
    private final Query query;

    Engines(Archives archives, final Query query) {
        this.archives = archives;
        this.query = query;
    }

    public Engine engine(final String engine) {
        if (engine == null || engine.isBlank()) {
            throw new IllegalArgumentException(engine);
        }
        return new Engine(this, this.query.addPath(engine));
    }

}
