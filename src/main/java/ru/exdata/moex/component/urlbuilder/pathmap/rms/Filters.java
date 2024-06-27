package ru.exdata.moex.component.urlbuilder.pathmap.rms;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Filters {
    private final Irr irr;
    private final Query query;

    Filters(Irr irr, final Query query) {
        this.irr = irr;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/766">Доступные параметры фильтрации для индикаторов рисков</a>
     */
    public Format format() {
        return new Format(irr.objects.engine.engines.rms.iss.issClient.httpClient, irr.objects.engine.engines.rms.iss.issClient.uri, this.query);
    }
}
