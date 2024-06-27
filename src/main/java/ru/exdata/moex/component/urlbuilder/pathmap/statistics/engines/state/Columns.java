package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.state;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Columns {
    private final Rates rates;
    private final Query query;

    Columns(Rates rates, final Query query) {
        this.rates = rates;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/179"></a>
     */
    public Format format() {
        return new Format(rates.state.engines.statistics.iss.issClient.httpClient, rates.state.engines.statistics.iss.issClient.uri, this.query);
    }
}
