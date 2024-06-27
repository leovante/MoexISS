package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.sessions.board;

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
     * <a href="https://iss.moex.com/iss/reference/821">Получить историю торгов для всех бумаг на указанном режиме торгов отфильтрованных по дате.</a>
     */
    public Format format() {
        return new Format(board.boards.session.sessions.market.markets.engine.engines.history.iss.issClient.httpClient, board.boards.session.sessions.market.markets.engine.engines.history.iss.issClient.uri, this.query);
    }

    public Security security(final String security) {
        if (security == null || security.isBlank()) {
            throw new IllegalArgumentException(security);
        }
        return new Security(this, this.query.addPath(security));
    }

}
