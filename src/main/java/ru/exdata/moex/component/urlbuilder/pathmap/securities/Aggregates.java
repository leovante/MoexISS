package ru.exdata.moex.component.urlbuilder.pathmap.securities;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Aggregates {
    private final Security security;
    private final Query query;

    Aggregates(Security security, final Query query) {
        this.security = security;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/214">Агрегированные итоги торгов за дату по рынкам</a>
     */
    public Format format() {
        return new Format(security.securities.iss.issClient.httpClient, security.securities.iss.issClient.uri, this.query);
    }
}
