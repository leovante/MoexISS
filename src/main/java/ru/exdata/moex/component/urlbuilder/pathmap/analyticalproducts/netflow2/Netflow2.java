package ru.exdata.moex.component.urlbuilder.pathmap.analyticalproducts.netflow2;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.analyticalproducts.AnalyticalProducts;

public final class Netflow2 {
    final AnalyticalProducts analyticalProducts;
    private final Query query;

    public Netflow2(AnalyticalProducts analyticalProducts, final Query query) {
        this.analyticalProducts = analyticalProducts;
        this.query = query;
    }

    public Securities securities() {
        return new Securities(this, this.query.addPath("securities"));
    }

}
