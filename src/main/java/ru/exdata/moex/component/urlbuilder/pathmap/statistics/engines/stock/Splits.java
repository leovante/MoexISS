package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Splits {
    final Stock stock;
    private final Query query;

    Splits(Stock stock, final Query query) {
        this.stock = stock;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/758">Справочник дроблений и консолидаций бумаг фондового рынка</a>
     */
    public Format format() {
        return new Format(stock.engines.statistics.iss.issClient.httpClient, stock.engines.statistics.iss.issClient.uri, this.query);
    }

    public Security security(final String security) {
        if (security == null || security.isBlank()) {
            throw new IllegalArgumentException(security);
        }
        return new Security(this, this.query.addPath(security));
    }

}
