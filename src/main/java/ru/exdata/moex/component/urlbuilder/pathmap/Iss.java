package ru.exdata.moex.component.urlbuilder.pathmap;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.analyticalproducts.AnalyticalProducts;
import ru.exdata.moex.component.urlbuilder.pathmap.archives.Archives;
import ru.exdata.moex.component.urlbuilder.pathmap.engines.Engines;
import ru.exdata.moex.component.urlbuilder.pathmap.history.History;
import ru.exdata.moex.component.urlbuilder.pathmap.rms.Rms;
import ru.exdata.moex.component.urlbuilder.pathmap.securities.Securities;
import ru.exdata.moex.component.urlbuilder.pathmap.securitygroups.Securitygroups;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.Statistics;

public final class Iss {

    public final IssClient issClient;
    private final Query query;

    Iss(IssClient issClient, final Query query) {
        this.issClient = issClient;
        this.query = query;
    }

    public Securitygroups securitygroups() {
        return new Securitygroups(this, this.query.addPath("securitygroups"));
    }

    public Sitenews sitenews() {
        return new Sitenews(this, this.query.addPath("sitenews"));
    }

    public Index index() {
        return new Index(this, this.query.addPath("index"));
    }

    public Statistics statistics() {
        return new Statistics(this, this.query.addPath("statistics"));
    }

    public Turnovers turnovers() {
        return new Turnovers(this, this.query.addPath("turnovers"));
    }

    public Engines engines() {
        return new Engines(this, this.query.addPath("engines"));
    }

    public History history() {
        return new History(this, this.query.addPath("history"));
    }

    public Rms rms() {
        return new Rms(this, this.query.addPath("rms"));
    }

    public Events events() {
        return new Events(this, this.query.addPath("events"));
    }

    public AnalyticalProducts analyticalproducts() {
        return new AnalyticalProducts(this, this.query.addPath("analyticalproducts"));
    }

    public Securities securities() {
        return new Securities(this, this.query.addPath("securities"));
    }

    public Archives archives() {
        return new Archives(this, this.query.addPath("archives"));
    }

    public Securitytypes securitytypes() {
        return new Securitytypes(this, this.query.addPath("securitytypes"));
    }

}
