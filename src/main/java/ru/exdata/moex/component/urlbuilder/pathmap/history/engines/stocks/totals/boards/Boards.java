package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.stocks.totals.boards;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.stocks.totals.Totals;

public final class Boards {
    final Totals totals;
    private final Query query;

    public Boards(Totals totals, final Query query) {
        this.totals = totals;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/161">Список режимов обобщенной информации по фондовому рынку</a>
     */
    public Format format() {
        return new Format(totals.stock.engines.history.iss.issClient.httpClient, totals.stock.engines.history.iss.issClient.uri, this.query);
    }

    public Board board(final String board) {
        if (board == null || board.isBlank()) {
            throw new IllegalArgumentException(board);
        }
        return new Board(this, this.query.addPath(board));
    }

}
