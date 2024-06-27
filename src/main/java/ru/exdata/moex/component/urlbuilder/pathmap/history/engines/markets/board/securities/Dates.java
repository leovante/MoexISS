package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.board.securities;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Dates {
    private final Security security;
    private final Query query;

    Dates(Security security, final Query query) {
        this.security = security;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/66">
     * Получить интервал дат в истории, за которые доступна указанная бумага на рынке на указанном режиме торгов.
     * </a>
     */
    public Format format() {
        return new Format(security.securities.board.boards.market.markets.engine.engines.history.iss.issClient.httpClient, security.securities.board.boards.market.markets.engine.engines.history.iss.issClient.uri, this.query);
    }
}
