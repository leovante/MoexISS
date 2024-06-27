package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.boardgroups.yields;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.boardgroups.Boardgroup;

public final class Yields {
    final Boardgroup boardgroup;
    private final Query query;

    public Yields(Boardgroup boardgroup, final Query query) {
        this.boardgroup = boardgroup;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/799">Получить доходности торгов для всех бумаг на указанной группе режимов торгов за указанную дату.</a>
     */
    public Format format() {
        return new Format(boardgroup.boardgroups.market.markets.engine.engines.history.iss.issClient.httpClient, boardgroup.boardgroups.market.markets.engine.engines.history.iss.issClient.uri, this.query);
    }

    public Security security(final String security) {
        if (security == null || security.isBlank()) {
            throw new IllegalArgumentException(security);
        }
        return new Security(this, this.query.addPath(security));
    }

}
