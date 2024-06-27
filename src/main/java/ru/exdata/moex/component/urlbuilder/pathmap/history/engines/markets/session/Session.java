package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.session;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.Market;

public final class Session {
    final Market market;
    private final Query query;

    public Session(Market market, final Query query) {
        this.market = market;
        this.query = query;
    }

    public Session_ session(final String session_) {
        if (session_ == null || session_.isBlank()) {
            throw new IllegalArgumentException(session_);
        }
        return new Session_(this, this.query.addPath(session_));
    }

}
