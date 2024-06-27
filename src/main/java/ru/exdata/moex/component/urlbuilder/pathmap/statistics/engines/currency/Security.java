package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.currency;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Security {
    private final Fixing fixing;
    private final Query query;

    Security(Fixing fixing, final Query query) {
        this.fixing = fixing;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/715">Фиксинги московской биржи</a>
     */
    public Format format() {
        return new Format(fixing.markets.currency.engines.statistics.iss.issClient.httpClient, fixing.markets.currency.engines.statistics.iss.issClient.uri, this.query);
    }
}
