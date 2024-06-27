package ru.exdata.moex.component.urlbuilder.pathmap.analyticalproducts.netflow2;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Security {
    private final Securities securities;
    private final Query query;

    Security(Securities securities, final Query query) {
        this.securities = securities;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/769"></a>
     */
    public Format format() {
        return new Format(securities.netflow2.analyticalProducts.iss.issClient.httpClient, securities.netflow2.analyticalProducts.iss.issClient.uri, this.query);
    }
}
