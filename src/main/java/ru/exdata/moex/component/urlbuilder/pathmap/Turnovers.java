package ru.exdata.moex.component.urlbuilder.pathmap;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Turnovers {
    final Iss iss;
    private final Query query;

    Turnovers(Iss iss, final Query query) {
        this.iss = iss;
        this.query = query;
    }

    public Columns columns() {
        return new Columns(this, this.query.addPath("columns"));
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/24">Получить сводные обороты по рынкам. например: https://iss.moex.com/iss/turnovers.xml</a>
     */
    public Format format() {
        return new Format(iss.issClient.httpClient, iss.issClient.uri, this.query);
    }

}
