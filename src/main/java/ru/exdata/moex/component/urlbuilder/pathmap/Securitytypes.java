package ru.exdata.moex.component.urlbuilder.pathmap;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Securitytypes {
    final Iss iss;
    private final Query query;

    Securitytypes(Iss iss, final Query query) {
        this.iss = iss;
        this.query = query;
    }

    public Securitytype securitytype(final String securitytype) {
        if (securitytype == null || securitytype.isBlank()) {
            throw new IllegalArgumentException(securitytype);
        }
        return new Securitytype(this, this.query.addPath(securitytype));
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/132">Типы ценных бумаг</a>
     */
    public Format format() {
        return new Format(iss.issClient.httpClient, iss.issClient.uri, this.query);
    }

}
