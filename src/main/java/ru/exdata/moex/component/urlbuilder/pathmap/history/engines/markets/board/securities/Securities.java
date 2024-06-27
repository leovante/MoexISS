package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.board.securities;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.board.Board;

public final class Securities {
    final Board board;
    private final Query query;

    public Securities(Board board, final Query query) {
        this.board = board;
        this.query = query;
    }

    public Security security(final String security) {
        if (security == null || security.isBlank()) {
            throw new IllegalArgumentException(security);
        }
        return new Security(this, this.query.addPath(security));
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/64">Получить историю торгов для всех бумаг на указанном режиме торгов отфильтрованных по дате.</a>
     */
    public Format format() {
        return new Format(board.boards.market.markets.engine.engines.history.iss.issClient.httpClient, board.boards.market.markets.engine.engines.history.iss.issClient.uri, this.query);
    }

}
