package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.sessions.boardgroups;

import ru.exdata.moex.component.urlbuilder.Query;

public final class Securities {
    final Boardgroup boardgroup;
    private final Query query;

    Securities(Boardgroup boardgroup, final Query query) {
        this.boardgroup = boardgroup;
        this.query = query;
    }

    public Security security(final String security) {
        if (security == null || security.isBlank()) {
            throw new IllegalArgumentException(security);
        }
        return new Security(this, this.query.addPath(security));
    }

}
