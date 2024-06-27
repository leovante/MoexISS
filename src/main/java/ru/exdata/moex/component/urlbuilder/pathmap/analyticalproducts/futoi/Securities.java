package ru.exdata.moex.component.urlbuilder.pathmap.analyticalproducts.futoi;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Securities {
    final Futoi futoi;
    private final Query query;

    Securities(Futoi futoi, final Query query) {
        this.futoi = futoi;
        this.query = query;
    }

    public Security security(final String security) {
        if (security == null || security.isBlank()) {
            throw new IllegalArgumentException(security);
        }
        return new Security(this, this.query.addPath(security));
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/807"></a>
     */
    public Format format() {
        return new Format(futoi.analyticalProducts.iss.issClient.httpClient, futoi.analyticalProducts.iss.issClient.uri, this.query);
    }

}
