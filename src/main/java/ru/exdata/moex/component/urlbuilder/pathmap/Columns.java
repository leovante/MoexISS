package ru.exdata.moex.component.urlbuilder.pathmap;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Columns {
    private final Turnovers turnovers;
    private final Query query;

    Columns(Turnovers turnovers, final Query query) {
        this.turnovers = turnovers;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/100">Получить описание полей для запросов оборотов по рынку/торговой системе. например: https://iss.moex.com/iss/engines/stock/turnovers/columns.xml</a>
     */
    public Format format() {
        return new Format(turnovers.iss.issClient.httpClient, turnovers.iss.issClient.uri, this.query);
    }
}
