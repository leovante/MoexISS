package ru.exdata.moex.component.urlbuilder.pathmap.engines.market;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.engines.market.boardgroup.Boardgroup;

public final class Boardgroups {
    public final Market market;
    private final Query query;

    Boardgroups(Market market, final Query query) {
        this.market = market;
        this.query = query;
    }

    public Boardgroup boardgroup(final String boardgroup) {
        if (boardgroup == null || boardgroup.isBlank()) {
            throw new IllegalArgumentException(boardgroup);
        }
        return new Boardgroup(this, this.query.addPath(boardgroup));
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/45">Получить справочник групп режимов торгов.</a>
     */
    public Format format() {
        return new Format(market.markets.engine.engines.iss.issClient.httpClient, market.markets.engine.engines.iss.issClient.uri, this.query);
    }

}
