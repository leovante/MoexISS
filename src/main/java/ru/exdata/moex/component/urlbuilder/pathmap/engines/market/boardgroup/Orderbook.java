package ru.exdata.moex.component.urlbuilder.pathmap.engines.market.boardgroup;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Orderbook {
    private final Boardgroup boardgroup;
    private final Query query;

    Orderbook(Boardgroup boardgroup, final Query query) {
        this.boardgroup = boardgroup;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/38">Получить лучшие заявки всех инструментов, торгуемых на выбранной группе режимов торгов.</a>
     */
    public Format format() {
        return new Format(boardgroup.boardgroups.market.markets.engine.engines.iss.issClient.httpClient, boardgroup.boardgroups.market.markets.engine.engines.iss.issClient.uri, this.query);
    }
}
