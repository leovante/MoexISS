package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.boardgroups.dates;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.boardgroups.Boardgroup;

public final class Dates {
    private final Boardgroup boardgroup;
    private final Query query;

    public Dates(Boardgroup boardgroup, final Query query) {
        this.boardgroup = boardgroup;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/51">Получить интервал дат для указанной группы режимов торгов.</a>
     */
    public Format format() {
        return new Format(boardgroup.boardgroups.market.markets.engine.engines.history.iss.issClient.httpClient, boardgroup.boardgroups.market.markets.engine.engines.history.iss.issClient.uri, this.query);
    }
}
