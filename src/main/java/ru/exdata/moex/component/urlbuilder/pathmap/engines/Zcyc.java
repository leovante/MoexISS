package ru.exdata.moex.component.urlbuilder.pathmap.engines;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Zcyc {
    private final Engine engine;
    private final Query query;

    Zcyc(Engine engine, final Query query) {
        this.engine = engine;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/634"></a>
     */
    public Format format() {
        return new Format(engine.engines.iss.issClient.httpClient, engine.engines.iss.issClient.uri, this.query);
    }
}
