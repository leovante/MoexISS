package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.engine;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Report_name {
    private final Monthly monthly;
    private final Query query;

    Report_name(Monthly monthly, final Query query) {
        this.monthly = monthly;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/220"></a>
     */
    public Format format() {
        return new Format(monthly.engine.engines.statistics.iss.issClient.httpClient, monthly.engine.engines.statistics.iss.issClient.uri, this.query);
    }
}
