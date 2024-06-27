package ru.exdata.moex.component.urlbuilder.pathmap.archives;

import ru.exdata.moex.component.urlbuilder.Query;

public final class Datatype {
    final Market market;
    private final Query query;

    Datatype(Market market, final Query query) {
        this.market = market;
        this.query = query;
    }

    public Period period(final String period) {
        if (period == null || period.isBlank()) {
            throw new IllegalArgumentException(period);
        }
        return new Period(this, this.query.addPath(period));
    }

    public Years years() {
        return new Years(this, this.query.addPath("years"));
    }

}
