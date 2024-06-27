package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Security {
    private final Splits splits;
    private final Query query;

    Security(Splits splits, final Query query) {
        this.splits = splits;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/759"></a>
     */
    public Format format() {
        return new Format(splits.stock.engines.statistics.iss.issClient.httpClient, splits.stock.engines.statistics.iss.issClient.uri, this.query);
    }
}
