package ru.exdata.moex.component.urlbuilder.pathmap.engines.market.boardgroup;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.engines.market.boardgroup.security.Security;

public final class Candleborders {
    private final Security security;
    private final Query query;

    public Candleborders(Security security, final Query query) {
        this.security = security;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/158"></a>
     */
    public Format format() {
        return new Format(security.securities.boardgroup.boardgroups.market.markets.engine.engines.iss.issClient.httpClient, security.securities.boardgroup.boardgroups.market.markets.engine.engines.iss.issClient.uri, this.query);
    }
}
