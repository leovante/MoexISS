package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.currency;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Fixing {
    final Markets markets;
    private final Query query;

    Fixing(Markets markets, final Query query) {
        this.markets = markets;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/716">Фиксинги московской биржи</a>
     */
    public Format format() {
        return new Format(markets.currency.engines.statistics.iss.issClient.httpClient, markets.currency.engines.statistics.iss.issClient.uri, this.query);
    }

    public Security security(final String security) {
        if (security == null || security.isBlank()) {
            throw new IllegalArgumentException(security);
        }
        return new Security(this, this.query.addPath(security));
    }

}
