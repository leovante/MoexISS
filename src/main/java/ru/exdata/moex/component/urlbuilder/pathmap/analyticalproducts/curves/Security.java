package ru.exdata.moex.component.urlbuilder.pathmap.analyticalproducts.curves;

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
     * <a href="https://iss.moex.com/iss/reference/861">Будущие ставки для ценообразования нестандартных инструментов (деривативов)</a>
     */
    public Format format() {
        return new Format(securities.curves.analyticalProducts.iss.issClient.httpClient, securities.curves.analyticalProducts.iss.issClient.uri, this.query);
    }
}
