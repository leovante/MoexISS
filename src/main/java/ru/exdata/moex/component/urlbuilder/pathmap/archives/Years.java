package ru.exdata.moex.component.urlbuilder.pathmap.archives;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Years {
    final Datatype datatype;
    private final Query query;

    Years(Datatype datatype, final Query query) {
        this.datatype = datatype;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/114">Список годов, за которые существуют ссылки на файлы с архивом сделок и исторической биржевой информацией. datatype может принимать значения securities или trades.</a>
     */
    public Format format() {
        return new Format(datatype.market.markets.engine.engines.archives.iss.issClient.httpClient, datatype.market.markets.engine.engines.archives.iss.issClient.uri, this.query);
    }

    public Year year(final String year) {
        if (year == null || year.isBlank()) {
            throw new IllegalArgumentException(year);
        }
        return new Year(this, this.query.addPath(year));
    }

}
