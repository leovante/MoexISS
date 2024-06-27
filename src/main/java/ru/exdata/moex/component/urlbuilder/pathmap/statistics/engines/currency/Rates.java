package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.currency;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Rates {
    private final Selt selt;
    private final Query query;

    Rates(Selt selt, final Query query) {
        this.selt = selt;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/168">Курсы цбрф</a>
     */
    public Format format() {
        return new Format(selt.markets.currency.engines.statistics.iss.issClient.httpClient, selt.markets.currency.engines.statistics.iss.issClient.uri, this.query);
    }
}
