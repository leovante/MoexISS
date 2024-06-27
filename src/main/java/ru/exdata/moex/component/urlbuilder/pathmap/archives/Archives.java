package ru.exdata.moex.component.urlbuilder.pathmap.archives;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.Iss;

public final class Archives {
    final Iss iss;
    private final Query query;

    public Archives(Iss iss, final Query query) {
        this.iss = iss;
        this.query = query;
    }

    public Engines engines() {
        return new Engines(this, this.query.addPath("engines"));
    }

}
