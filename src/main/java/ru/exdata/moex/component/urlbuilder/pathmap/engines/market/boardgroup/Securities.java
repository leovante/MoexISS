package ru.exdata.moex.component.urlbuilder.pathmap.engines.market.boardgroup;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.engines.market.boardgroup.security.Security;

public final class Securities {
    public final Boardgroup boardgroup;
    private final Query query;

    Securities(Boardgroup boardgroup, final Query query) {
        this.boardgroup = boardgroup;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/29">Получить список всех инструментов, торгуемых на выбранной группе режимов торгов.</a>
     */
    public Format format() {
        return new Format(boardgroup.boardgroups.market.markets.engine.engines.iss.issClient.httpClient, boardgroup.boardgroups.market.markets.engine.engines.iss.issClient.uri, this.query);
    }

    public Security security(final String security) {
        if (security == null || security.isBlank()) {
            throw new IllegalArgumentException(security);
        }
        return new Security(this, this.query.addPath(security));
    }

}
