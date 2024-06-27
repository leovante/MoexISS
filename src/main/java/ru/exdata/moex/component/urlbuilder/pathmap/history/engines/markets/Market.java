package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.board.Boards;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.boardgroups.Boardgroups;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.securities.Securities;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.session.Session;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.sessions.Sessions;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.yields.Yields;

public final class Market {
    public final Markets markets;
    private final Query query;

    Market(Markets markets, final Query query) {
        this.markets = markets;
        this.query = query;
    }

    public Securities securities() {
        return new Securities(this, this.query.addPath("securities"));
    }

    public Listing listing() {
        return new Listing(this, this.query.addPath("listing"));
    }

    public Boards boards() {
        return new Boards(this, this.query.addPath("boards"));
    }

    public Yields yields() {
        return new Yields(this, this.query.addPath("yields"));
    }

    public Dates dates() {
        return new Dates(this, this.query.addPath("dates"));
    }

    public Sessions sessions() {
        return new Sessions(this, this.query.addPath("sessions"));
    }

    public Session session() {
        return new Session(this, this.query.addPath("session"));
    }

    public Boardgroups boardgroups() {
        return new Boardgroups(this, this.query.addPath("boardgroups"));
    }

}
