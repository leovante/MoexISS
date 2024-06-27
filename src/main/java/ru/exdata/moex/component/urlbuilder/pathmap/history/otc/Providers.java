package ru.exdata.moex.component.urlbuilder.pathmap.history.otc;

import ru.exdata.moex.component.urlbuilder.Query;

public final class Providers {
    final Otc otc;
    private final Query query;

    Providers(Otc otc, final Query query) {
        this.otc = otc;
        this.query = query;
    }

    public Nsd nsd() {
        return new Nsd(this, this.query.addPath("nsd"));
    }

}
