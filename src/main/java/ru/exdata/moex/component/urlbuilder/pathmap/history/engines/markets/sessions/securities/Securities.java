package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.sessions.securities;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.sessions.Session;

public final class Securities {
    final Session session;
    private final Query query;

    public Securities(Session session, final Query query) {
        this.session = session;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/813">Получить историю по всем бумагам на рынке за одну дату. например: https://iss.moex.com/iss/history/engines/stock/markets/index/securities.xml?date=2010-11-22</a>
     */
    public Format format() {
        return new Format(session.sessions.market.markets.engine.engines.history.iss.issClient.httpClient, session.sessions.market.markets.engine.engines.history.iss.issClient.uri, this.query);
    }

    public Security security(final String security) {
        if (security == null || security.isBlank()) {
            throw new IllegalArgumentException(security);
        }
        return new Security(this, this.query.addPath(security));
    }

}
