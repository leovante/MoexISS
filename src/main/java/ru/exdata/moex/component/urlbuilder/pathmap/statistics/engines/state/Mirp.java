package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.state;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Mirp {
    private final Repo repo;
    private final Query query;

    Mirp(Repo repo, final Query query) {
        this.repo = repo;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/165"></a>
     */
    public Format format() {
        return new Format(repo.markets.state.engines.statistics.iss.issClient.httpClient, repo.markets.state.engines.statistics.iss.issClient.uri, this.query);
    }
}
