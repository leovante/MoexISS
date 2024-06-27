package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.sessions;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.sessions.board.Boards;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.sessions.boardgroups.Boardgroups;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.sessions.securities.Securities;

public final class Session {
    public final Sessions sessions;
    private final Query query;

    Session(Sessions sessions, final Query query) {
        this.sessions = sessions;
        this.query = query;
    }

    public Securities securities() {
        return new Securities(this, this.query.addPath("securities"));
    }

    public Boards boards() {
        return new Boards(this, this.query.addPath("boards"));
    }

    public Boardgroups boardgroups() {
        return new Boardgroups(this, this.query.addPath("boardgroups"));
    }

}
