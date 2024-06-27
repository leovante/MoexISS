package ru.exdata.moex.component.urlbuilder.pathmap.securitygroups;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Securities {
    private final Collection collection;
    private final Query query;

    Securities(Collection collection, final Query query) {
        this.collection = collection;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/131">Описание инструментов</a>
     */
    public Format format() {
        return new Format(collection.collections.securitygroup.securitygroups.iss.issClient.httpClient, collection.collections.securitygroup.securitygroups.iss.issClient.uri, this.query);
    }
}
