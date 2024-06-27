package ru.exdata.moex.component.urlbuilder.pathmap.archives;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Period {
    private final Datatype datatype;
    private final Query query;

    Period(Datatype datatype, final Query query) {
        this.datatype = datatype;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/116">Получить список ccылок на годовые/месячные/дневные файлы с архивом сделок и исторической биржевой информацией. datatype может принимать значения securities или trades. period может принимать значения yearly, monthly или daily. помесячные данные доступны только за последние 30 дней.</a>
     */
    public Format format() {
        return new Format(datatype.market.markets.engine.engines.archives.iss.issClient.httpClient, datatype.market.markets.engine.engines.archives.iss.issClient.uri, this.query);
    }
}
