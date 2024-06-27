package ru.exdata.moex.component.urlbuilder.pathmap.engines.market.board.security;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Orderbook {
    private final Security security;
    private final Query query;

    Orderbook(Security security, final Query query) {
        this.security = security;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/57">Получить стакан котировок указанного инструмента по выбранному режиму торгов.</a>
     */
    public Format format() {
        return new Format(security.securities.board.boards.market.markets.engine.engines.iss.issClient.httpClient, security.securities.board.boards.market.markets.engine.engines.iss.issClient.uri, this.query);
    }
}
