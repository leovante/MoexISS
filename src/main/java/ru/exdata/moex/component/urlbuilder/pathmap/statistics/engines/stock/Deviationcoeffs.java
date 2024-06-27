package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.stock;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Deviationcoeffs {
    private final Stock stock;
    private final Query query;

    Deviationcoeffs(Stock stock, final Query query) {
        this.stock = stock;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/134">Показатели для определения критериев существенного отклонения</a>
     */
    public Format format() {
        return new Format(stock.engines.statistics.iss.issClient.httpClient, stock.engines.statistics.iss.issClient.uri, this.query);
    }
}
