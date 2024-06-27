package ru.exdata.moex.component.urlbuilder.pathmap.analyticalproducts.netflow2;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Securities {
    final Netflow2 netflow2;
    private final Query query;

    Securities(Netflow2 netflow2, final Query query) {
        this.netflow2 = netflow2;
        this.query = query;
    }

    public Security security(final String security) {
        if (security == null || security.isBlank()) {
            throw new IllegalArgumentException(security);
        }
        return new Security(this, this.query.addPath(security));
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/767"></a>
     */
    public Format format() {
        return new Format(netflow2.analyticalProducts.iss.issClient.httpClient, netflow2.analyticalProducts.iss.issClient.uri, this.query);
    }

}
