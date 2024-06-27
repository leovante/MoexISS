package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.state;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Rates {
    final State state;
    private final Query query;

    Rates(State state, final Query query) {
        this.state = state;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/178"></a>
     */
    public Format format() {
        return new Format(state.engines.statistics.iss.issClient.httpClient, state.engines.statistics.iss.issClient.uri, this.query);
    }

    public Columns columns() {
        return new Columns(this, this.query.addPath("columns"));
    }

}
