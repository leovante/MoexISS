package ru.exdata.moex.component.urlbuilder.pathmap.securitygroups;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Collection {
    final Collections collections;
    private final Query query;

    Collection(Collections collections, final Query query) {
        this.collections = collections;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/130">Коллекция ценных бумаг входящие в группу</a>
     */
    public Format format() {
        return new Format(collections.securitygroup.securitygroups.iss.issClient.httpClient, collections.securitygroup.securitygroups.iss.issClient.uri, this.query);
    }

    public Securities securities() {
        return new Securities(this, this.query.addPath("securities"));
    }

}
