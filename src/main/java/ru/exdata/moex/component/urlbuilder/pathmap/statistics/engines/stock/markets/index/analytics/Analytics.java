package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.markets.index.analytics;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.markets.index.Index;

public final class Analytics {
    final Index index;
    private final Query query;

    public Analytics(Index index, final Query query) {
        this.index = index;
        this.query = query;
    }

    public Indexid indexid(final String indexid) {
        if (indexid == null || indexid.isBlank()) {
            throw new IllegalArgumentException(indexid);
        }
        return new Indexid(this, this.query.addPath(indexid));
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/146">Индексы фондового рынка</a>
     */
    public Format format() {
        return new Format(index.markets.stock.engines.statistics.iss.issClient.httpClient, index.markets.stock.engines.statistics.iss.issClient.uri, this.query);
    }

    public Columns columns() {
        return new Columns(this, this.query.addPath("columns"));
    }

}
