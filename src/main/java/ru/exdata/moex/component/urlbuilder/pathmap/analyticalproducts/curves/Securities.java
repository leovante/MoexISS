package ru.exdata.moex.component.urlbuilder.pathmap.analyticalproducts.curves;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Securities {
    final Curves curves;
    private final Query query;

    Securities(Curves curves, final Query query) {
        this.curves = curves;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/859">Будущие ставки для ценообразования нестандартных инструментов (деривативов)</a>
     */
    public Format format() {
        return new Format(curves.analyticalProducts.iss.issClient.httpClient, curves.analyticalProducts.iss.issClient.uri, this.query);
    }

    public Security security(final String security) {
        if (security == null || security.isBlank()) {
            throw new IllegalArgumentException(security);
        }
        return new Security(this, this.query.addPath(security));
    }

}
