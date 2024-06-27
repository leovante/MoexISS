package ru.exdata.moex.component.urlbuilder.pathmap.engines.market.security;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Candleborders {
    private final Security security;
    private final Query query;

    Candleborders(Security security, final Query query) {
        this.security = security;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/156"></a>
     */
    public Format format() {
        return new Format(security.securities.market.markets.engine.engines.iss.issClient.httpClient, security.securities.market.markets.engine.engines.iss.issClient.uri, this.query);
    }
}
