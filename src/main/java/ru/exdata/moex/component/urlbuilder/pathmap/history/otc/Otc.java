package ru.exdata.moex.component.urlbuilder.pathmap.history.otc;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.history.History;

public final class Otc {
    final History history;
    private final Query query;

    public Otc(History history, final Query query) {
        this.history = history;
        this.query = query;
    }

    public Providers providers() {
        return new Providers(this, this.query.addPath("providers"));
    }

}
