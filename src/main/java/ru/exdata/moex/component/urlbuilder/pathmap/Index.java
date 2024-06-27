package ru.exdata.moex.component.urlbuilder.pathmap;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Index {
    private final Iss iss;
    private final Query query;

    Index(Iss iss, final Query query) {
        this.iss = iss;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/28">Получить глобальные справочники iss. например: https://iss.moex.com/iss/index.xml</a>
     */
    public Format format() {
        return new Format(iss.issClient.httpClient, iss.issClient.uri, this.query);
    }
}
