package ru.exdata.moex.component.urlbuilder.pathmap.securities;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.Iss;

public final class Securities {
    final Iss iss;
    private final Query query;

    public Securities(Iss iss, final Query query) {
        this.iss = iss;
        this.query = query;
    }

    public Security security(final String security) {
        if (security == null || security.isBlank()) {
            throw new IllegalArgumentException(security);
        }
        return new Security(this, this.query.addPath(security));
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/5">Список бумаг торгуемых на московской бирже.</a>
     */
    public Format format() {
        return new Format(iss.issClient.httpClient, iss.issClient.uri, this.query);
    }

}
