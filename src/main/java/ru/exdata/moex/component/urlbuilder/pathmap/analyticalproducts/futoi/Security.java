package ru.exdata.moex.component.urlbuilder.pathmap.analyticalproducts.futoi;

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
     * <a href="https://iss.moex.com/iss/reference/809"></a>
     */
    public Format format() {
        return new Format(securities.futoi.analyticalProducts.iss.issClient.httpClient, securities.futoi.analyticalProducts.iss.issClient.uri, this.query);
    }
}
