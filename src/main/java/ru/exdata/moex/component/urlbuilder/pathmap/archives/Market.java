package ru.exdata.moex.component.urlbuilder.pathmap.archives;

import ru.exdata.moex.component.urlbuilder.Query;

public final class Market {
    final Markets markets;
    private final Query query;

    Market(Markets markets, final Query query) {
        this.markets = markets;
        this.query = query;
    }

    public Datatype datatype(final String datatype) {
        if (datatype == null || datatype.isBlank()) {
            throw new IllegalArgumentException(datatype);
        }
        return new Datatype(this, this.query.addPath(datatype));
    }

}
