package ru.exdata.moex.component.urlbuilder.pathmap.analyticalproducts;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.Iss;
import ru.exdata.moex.component.urlbuilder.pathmap.analyticalproducts.curves.Curves;
import ru.exdata.moex.component.urlbuilder.pathmap.analyticalproducts.futoi.Futoi;
import ru.exdata.moex.component.urlbuilder.pathmap.analyticalproducts.netflow2.Netflow2;

public final class AnalyticalProducts {
    public final Iss iss;
    private final Query query;

    public AnalyticalProducts(Iss iss, final Query query) {
        this.iss = iss;
        this.query = query;
    }

    public Netflow2 netflow2() {
        return new Netflow2(this, this.query.addPath("netflow2"));
    }

    public Futoi futoi() {
        return new Futoi(this, this.query.addPath("futoi"));
    }

    public Curves curves() {
        return new Curves(this, this.query.addPath("curves"));
    }

}
