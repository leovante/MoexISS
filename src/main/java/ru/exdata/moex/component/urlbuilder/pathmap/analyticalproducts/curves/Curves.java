package ru.exdata.moex.component.urlbuilder.pathmap.analyticalproducts.curves;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.analyticalproducts.AnalyticalProducts;

public final class Curves {
    final AnalyticalProducts analyticalProducts;
    private final Query query;

    public Curves(AnalyticalProducts analyticalProducts, final Query query) {
        this.analyticalProducts = analyticalProducts;
        this.query = query;
    }

    public Securities securities() {
        return new Securities(this, this.query.addPath("securities"));
    }

}
