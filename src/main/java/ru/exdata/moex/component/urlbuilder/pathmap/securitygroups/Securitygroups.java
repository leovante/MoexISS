package ru.exdata.moex.component.urlbuilder.pathmap.securitygroups;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.Iss;

public final class Securitygroups {
    final Iss iss;
    private final Query query;

    public Securitygroups(Iss iss, final Query query) {
        this.iss = iss;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/127">Группы ценных бумаг</a>
     */
    public Format format() {
        return new Format(iss.issClient.httpClient, iss.issClient.uri, this.query);
    }

    public Securitygroup securitygroup(final String securitygroup) {
        if (securitygroup == null || securitygroup.isBlank()) {
            throw new IllegalArgumentException(securitygroup);
        }
        return new Securitygroup(this, this.query.addPath(securitygroup));
    }

}
