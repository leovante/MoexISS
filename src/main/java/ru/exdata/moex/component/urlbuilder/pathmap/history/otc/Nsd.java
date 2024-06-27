package ru.exdata.moex.component.urlbuilder.pathmap.history.otc;

import ru.exdata.moex.component.urlbuilder.Query;

public final class Nsd {
    final Providers providers;
    private final Query query;

    Nsd(Providers providers, final Query query) {
        this.providers = providers;
        this.query = query;
    }

    public Markets markets() {
        return new Markets(this, this.query.addPath("markets"));
    }

}
