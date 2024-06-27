package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.markets.index.rusfar;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.markets.index.Index;

public final class Rusfar {
    private final Index index;
    private final Query query;

    public Rusfar(Index index, final Query query) {
        this.index = index;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/843">Rusfar расшифровка показателей</a>
     */
    public Format format() {
        return new Format(index.markets.stock.engines.statistics.iss.issClient.httpClient, index.markets.stock.engines.statistics.iss.issClient.uri, this.query);
    }
}
