package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.futures;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Securities {
    final Indicativerates indicativerates;
    private final Query query;

    Securities(Indicativerates indicativerates, final Query query) {
        this.indicativerates = indicativerates;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/711">Индикативные курсы валют срочного рынка</a>
     */
    public Format format() {
        return new Format(indicativerates.markets.futures.engines.statistics.iss.issClient.httpClient, indicativerates.markets.futures.engines.statistics.iss.issClient.uri, this.query);
    }

    public Security security(final String security) {
        if (security == null || security.isBlank()) {
            throw new IllegalArgumentException(security);
        }
        return new Security(this, this.query.addPath(security));
    }

}
