package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.stocks.totals.boards;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Securities {
    final Board board;
    private final Query query;

    Securities(Board board, final Query query) {
        this.board = board;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/163">Обобщенная информация по фондовому рынку по выбранному режиму</a>
     */
    public Format format() {
        return new Format(board.boards.totals.stock.engines.history.iss.issClient.httpClient, board.boards.totals.stock.engines.history.iss.issClient.uri, this.query);
    }

    public Security security(final String security) {
        if (security == null || security.isBlank()) {
            throw new IllegalArgumentException(security);
        }
        return new Security(this, this.query.addPath(security));
    }

}
