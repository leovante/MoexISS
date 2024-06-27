package ru.exdata.moex.component.urlbuilder.pathmap.archives;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Months {
    private final Year year;
    private final Query query;

    Months(Year year, final Query query) {
        this.year = year;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/115">Список месяцев в году, за которые существуют ссылки на файлы с архивом сделок и исторической биржевой информацией. datatype может принимать значения securities или trades.</a>
     */
    public Format format() {
        return new Format(year.years.datatype.market.markets.engine.engines.archives.iss.issClient.httpClient, year.years.datatype.market.markets.engine.engines.archives.iss.issClient.uri, this.query);
    }
}
