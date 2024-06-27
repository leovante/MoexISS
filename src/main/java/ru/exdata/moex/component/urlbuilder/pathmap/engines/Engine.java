package ru.exdata.moex.component.urlbuilder.pathmap.engines;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Engine {
    public final Engines engines;
    private final Query query;

    Engine(Engines engines, final Query query) {
        this.engines = engines;
        this.query = query;
    }

    public Turnovers turnovers() {
        return new Turnovers(this, this.query.addPath("turnovers"));
    }

    public Zcyc zcyc() {
        return new Zcyc(this, this.query.addPath("zcyc"));
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/41">Получить описание и режим работы торговой системы. например: https://iss.moex.com/iss/engines/stock.xml</a>
     */
    public Format format() {
        return new Format(engines.iss.issClient.httpClient, engines.iss.issClient.uri, this.query);
    }

    public Markets markets() {
        return new Markets(this, this.query.addPath("markets"));
    }

}
