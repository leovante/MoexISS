package ru.exdata.moex.component.urlbuilder.pathmap.securities;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Indices {
    private final Security security;
    private final Query query;

    Indices(Security security, final Query query) {
        this.security = security;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/160">Список индексов в которые входит бумага</a>
     */
    public Format format() {
        return new Format(security.securities.iss.issClient.httpClient, security.securities.iss.issClient.uri, this.query);
    }
}
