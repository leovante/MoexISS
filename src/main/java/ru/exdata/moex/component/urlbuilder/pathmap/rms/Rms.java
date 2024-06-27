package ru.exdata.moex.component.urlbuilder.pathmap.rms;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.Iss;

public final class Rms {
    final Iss iss;
    private final Query query;

    public Rms(Iss iss, final Query query) {
        this.iss = iss;
        this.query = query;
    }

    public Engines engines() {
        return new Engines(this, this.query.addPath("engines"));
    }

}
