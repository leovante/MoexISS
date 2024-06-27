package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Dates {
    private final Market market;
    private final Query query;

    Dates(Market market, final Query query) {
        this.market = market;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/83">Получить даты, за которые доступны данные на указанных рынке и торговой системе.</a>
     */
    public Format format() {
        return new Format(market.markets.engine.engines.history.iss.issClient.httpClient, market.markets.engine.engines.history.iss.issClient.uri, this.query);
    }
}
