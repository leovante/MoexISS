package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.markets.bonds;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.markets.bonds.Aggregates;

public final class Columns {
    private final Aggregates aggregates;
    private final Query query;

    Columns(Aggregates aggregates, final Query query) {
        this.aggregates = aggregates;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/196"></a>
     */
    public Format format() {
        return new Format(aggregates.bonds.markets.stock.engines.statistics.iss.issClient.httpClient, aggregates.bonds.markets.stock.engines.statistics.iss.issClient.uri, this.query);
    }
}
