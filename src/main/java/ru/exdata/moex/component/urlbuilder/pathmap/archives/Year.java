package ru.exdata.moex.component.urlbuilder.pathmap.archives;

import ru.exdata.moex.component.urlbuilder.Query;

public final class Year {
    final Years years;
    private final Query query;

    Year(Years years, final Query query) {
        this.years = years;
        this.query = query;
    }

    public Months months() {
        return new Months(this, this.query.addPath("months"));
    }

}
