package ru.exdata.moex.component.urlbuilder.pathmap.history;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.Iss;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.Engines;
import ru.exdata.moex.component.urlbuilder.pathmap.history.otc.Otc;

public final class History {
    public final Iss iss;
    private final Query query;

    public History(Iss iss, final Query query) {
        this.iss = iss;
        this.query = query;
    }

    public Otc otc() {
        return new Otc(this, this.query.addPath("otc"));
    }

    public Engines engines() {
        return new Engines(this, this.query.addPath("engines"));
    }

}
