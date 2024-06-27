package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock.markets.bonds;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Aggregates {
    final Bonds bonds;
    private final Query query;

    Aggregates(Bonds bonds, final Query query) {
        this.bonds = bonds;
        this.query = query;
    }

    public Columns columns() {
        return new Columns(this, this.query.addPath("columns"));
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/195">Агрегированные показатели рынка облигаций</a>
     */
    public Format format() {
        return new Format(bonds.markets.stock.engines.statistics.iss.issClient.httpClient, bonds.markets.stock.engines.statistics.iss.issClient.uri, this.query);
    }

}
