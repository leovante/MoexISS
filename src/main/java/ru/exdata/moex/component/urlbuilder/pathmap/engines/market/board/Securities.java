package ru.exdata.moex.component.urlbuilder.pathmap.engines.market.board;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.engines.market.board.security.Security;

public final class Securities {
    public final Board board;
    private final Query query;

    Securities(Board board, final Query query) {
        this.board = board;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/32">Получить таблицу инструментов по режиму торгов. например: https://iss.moex.com/iss/engines/stock/markets/shares/boards/tqbr/securities.xml</a>
     */
    public Format format() {
        return new Format(board.boards.market.markets.engine.engines.iss.issClient.httpClient, board.boards.market.markets.engine.engines.iss.issClient.uri, this.query);
    }

    public Security security(final String security) {
        if (security == null || security.isBlank()) {
            throw new IllegalArgumentException(security);
        }
        return new Security(this, this.query.addPath(security));
    }

}
