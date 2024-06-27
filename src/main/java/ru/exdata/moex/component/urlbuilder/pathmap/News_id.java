package ru.exdata.moex.component.urlbuilder.pathmap;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class News_id {
    private final Sitenews sitenews;
    private final Query query;

    News_id(Sitenews sitenews, final Query query) {
        this.sitenews = sitenews;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/192">Новость сайта</a>
     */
    public Format format() {
        return new Format(sitenews.iss.issClient.httpClient, sitenews.iss.issClient.uri, this.query);
    }
}
