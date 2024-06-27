package ru.exdata.moex.component.urlbuilder.pathmap.rms;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Irr {
    final Objects objects;
    private final Query query;

    Irr(Objects objects, final Query query) {
        this.objects = objects;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/764">Индикаторы риска</a>
     */
    public Format format() {
        return new Format(objects.engine.engines.rms.iss.issClient.httpClient, objects.engine.engines.rms.iss.issClient.uri, this.query);
    }

    public Filters filters() {
        return new Filters(this, this.query.addPath("filters"));
    }

}
