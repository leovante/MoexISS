package ru.exdata.moex.component.urlbuilder.pathmap;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Sitenews {
    final Iss iss;
    private final Query query;

    Sitenews(Iss iss, final Query query) {
        this.iss = iss;
        this.query = query;
    }

    public News_id news_id(final String news_id) {
        if (news_id == null || news_id.isBlank()) {
            throw new IllegalArgumentException(news_id);
        }
        return new News_id(this, this.query.addPath(news_id));
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/191">Новости биржи</a>
     */
    public Format format() {
        return new Format(iss.issClient.httpClient, iss.issClient.uri, this.query);
    }

}
