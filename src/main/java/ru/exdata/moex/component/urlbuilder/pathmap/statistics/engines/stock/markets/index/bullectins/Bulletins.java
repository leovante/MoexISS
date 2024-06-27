package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.markets.index.bullectins;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.markets.index.Index;

public final class Bulletins {
    private final Index index;
    private final Query query;

    public Bulletins(Index index, final Query query) {
        this.index = index;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/839">Бюллетени для индексов</a>
     */
    public Format format() {
        return new Format(index.markets.stock.engines.statistics.iss.issClient.httpClient, index.markets.stock.engines.statistics.iss.issClient.uri, this.query);
    }
}
