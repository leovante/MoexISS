package ru.exdata.moex.component.urlbuilder.pathmap.engines;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.Iss;

public final class Engines {
    public final Iss iss;
    private final Query query;

    public Engines(Iss iss, final Query query) {
        this.iss = iss;
        this.query = query;
    }

    public Engine engine(final String engine) {
        if (engine == null || engine.isBlank()) {
            throw new IllegalArgumentException(engine);
        }
        return new Engine(this, this.query.addPath(engine));
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/40">Получить доступные торговые системы. например: https://iss.moex.com/iss/engines.xml</a>
     */
    public Format format() {
        return new Format(iss.issClient.httpClient, iss.issClient.uri, this.query);
    }

}
