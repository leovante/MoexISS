package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.board.yields;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Security {
    private final Yields yields;
    private final Query query;

    Security(Yields yields, final Query query) {
        this.yields = yields;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/797">Получить историю доходностей для указанной бумаги на указанном режиме торгов за указанный интервал дат.</a>
     */
    public Format format() {
        return new Format(yields.board.boards.market.markets.engine.engines.history.iss.issClient.httpClient, yields.board.boards.market.markets.engine.engines.history.iss.issClient.uri, this.query);
    }
}
