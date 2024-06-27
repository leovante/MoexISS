package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.sessions;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.Market;

public final class Sessions {
    public final Market market;
    private final Query query;

    public Sessions(Market market, final Query query) {
        this.market = market;
        this.query = query;
    }

    public Session session(final String session) {
        if (session == null || session.isBlank()) {
            throw new IllegalArgumentException(session);
        }
        return new Session(this, this.query.addPath(session));
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/811">Список сессий доступных в итогах торгов. только для фондового рынка!</a>
     */
    public Format format() {
        return new Format(market.markets.engine.engines.history.iss.issClient.httpClient, market.markets.engine.engines.history.iss.issClient.uri, this.query);
    }

}
