package ru.exdata.moex.component.urlbuilder.pathmap;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Securitytype {
    private final Securitytypes securitytypes;
    private final Query query;

    Securitytype(Securitytypes securitytypes, final Query query) {
        this.securitytypes = securitytypes;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/133">Справочник: тип ценной бумаги</a>
     */
    public Format format() {
        return new Format(securitytypes.iss.issClient.httpClient, securitytypes.iss.issClient.uri, this.query);
    }
}
