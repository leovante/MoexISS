package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.sessions.boardgroups;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Security {
    private final Securities securities;
    private final Query query;

    Security(Securities securities, final Query query) {
        this.securities = securities;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/819">Получить историю торгов для указанной бумаги на выбранной группе режимов торгов за указанный интервал дат.</a>
     */
    public Format format() {
        return new Format(securities.boardgroup.boardgroups.session.sessions.market.markets.engine.engines.history.iss.issClient.httpClient, securities.boardgroup.boardgroups.session.sessions.market.markets.engine.engines.history.iss.issClient.uri, this.query);
    }
}
