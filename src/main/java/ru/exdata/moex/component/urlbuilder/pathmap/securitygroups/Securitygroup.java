package ru.exdata.moex.component.urlbuilder.pathmap.securitygroups;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Securitygroup {
    final Securitygroups securitygroups;
    private final Query query;

    Securitygroup(Securitygroups securitygroups, final Query query) {
        this.securitygroups = securitygroups;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/128">Группа ценных бумаг</a>
     */
    public Format format() {
        return new Format(securitygroups.iss.issClient.httpClient, securitygroups.iss.issClient.uri, this.query);
    }

    public Collections collections() {
        return new Collections(this, this.query.addPath("collections"));
    }

}
