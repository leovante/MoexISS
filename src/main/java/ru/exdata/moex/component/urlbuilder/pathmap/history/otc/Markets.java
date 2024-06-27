package ru.exdata.moex.component.urlbuilder.pathmap.history.otc;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Markets {
    final Nsd nsd;
    private final Query query;

    Markets(Nsd nsd, final Query query) {
        this.nsd = nsd;
        this.query = query;
    }

    public Market market(final String market) {
        if (market == null || market.isBlank()) {
            throw new IllegalArgumentException(market);
        }
        return new Market(this, this.query.addPath(market));
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/833">Обобщенные данные отс пфи и репо - список рынков.</a>
     */
    public Format format() {
        return new Format(nsd.providers.otc.history.iss.issClient.httpClient, nsd.providers.otc.history.iss.issClient.uri, this.query);
    }

}
