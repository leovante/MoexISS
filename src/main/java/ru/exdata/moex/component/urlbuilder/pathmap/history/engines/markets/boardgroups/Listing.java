package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.boardgroups;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Listing {
    private final Boardgroup boardgroup;
    private final Query query;

    Listing(Boardgroup boardgroup, final Query query) {
        this.boardgroup = boardgroup;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/120">Получить данные по листингу бумаг в историческом разрезе по указанной группе режимов</a>
     */
    public Format format() {
        return new Format(boardgroup.boardgroups.market.markets.engine.engines.history.iss.issClient.httpClient, boardgroup.boardgroups.market.markets.engine.engines.history.iss.issClient.uri, this.query);
    }
}
