package ru.exdata.moex.component.urlbuilder.pathmap.analyticalproducts.futoi;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.analyticalproducts.AnalyticalProducts;

public final class Futoi {
    final AnalyticalProducts analyticalProducts;
    private final Query query;

    public Futoi(AnalyticalProducts analyticalProducts, final Query query) {
        this.analyticalProducts = analyticalProducts;
        this.query = query;
    }

    public Securities securities() {
        return new Securities(this, this.query.addPath("securities"));
    }

}
