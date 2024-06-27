package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.stocks.totals.boards;

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
     * <a href="https://iss.moex.com/iss/reference/164">Обобщенная информация по фондовому рынку по выбранному режиму и инструменту</a>
     */
    public Format format() {
        return new Format(securities.board.boards.totals.stock.engines.history.iss.issClient.httpClient, securities.board.boards.totals.stock.engines.history.iss.issClient.uri, this.query);
    }
}
