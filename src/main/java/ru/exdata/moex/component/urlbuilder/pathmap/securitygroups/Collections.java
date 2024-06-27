package ru.exdata.moex.component.urlbuilder.pathmap.securitygroups;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Collections {
    final Securitygroup securitygroup;
    private final Query query;

    Collections(Securitygroup securitygroup, final Query query) {
        this.securitygroup = securitygroup;
        this.query = query;
    }

    public Collection collection(final String collection) {
        if (collection == null || collection.isBlank()) {
            throw new IllegalArgumentException(collection);
        }
        return new Collection(this, this.query.addPath(collection));
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/129">Коллекции ценных бумаг входящие в группу</a>
     */
    public Format format() {
        return new Format(securitygroup.securitygroups.iss.issClient.httpClient, securitygroup.securitygroups.iss.issClient.uri, this.query);
    }

}
