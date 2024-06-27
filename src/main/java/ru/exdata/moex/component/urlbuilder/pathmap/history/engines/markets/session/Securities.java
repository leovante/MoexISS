package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.session;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Securities {
    private final Boardgroup boardgroup;
    private final Query query;

    Securities(Boardgroup boardgroup, final Query query) {
        this.boardgroup = boardgroup;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/825">Получить историю торгов для всех бумаг на указанной группе режимов торгов за указанную дату.</a>
     */
    public Format format() {
        return new Format(boardgroup.boardgroups.session.session.market.markets.engine.engines.history.iss.issClient.httpClient, boardgroup.boardgroups.session.session.market.markets.engine.engines.history.iss.issClient.uri, this.query);
    }
}
