package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.boardgroups;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.Market;

public final class Boardgroups {
    public final Market market;
    private final Query query;

    public Boardgroups(Market market, final Query query) {
        this.market = market;
        this.query = query;
    }

    public Boardgroup boardgroup(final String boardgroup) {
        if (boardgroup == null || boardgroup.isBlank()) {
            throw new IllegalArgumentException(boardgroup);
        }
        return new Boardgroup(this, this.query.addPath(boardgroup));
    }

}
