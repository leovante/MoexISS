package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.futures;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Asset {
    final Assets assets;
    private final Query query;

    Asset(Assets assets, final Query query) {
        this.assets = assets;
        this.query = query;
    }

    public Optionboard optionboard() {
        return new Optionboard(this, this.query.addPath("optionboard"));
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/877">Опционные серии</a>
     */
    public Format format() {
        return new Format(assets.options.markets.futures.engines.statistics.iss.issClient.httpClient, assets.options.markets.futures.engines.statistics.iss.issClient.uri, this.query);
    }

    public Volumes volumes() {
        return new Volumes(this, this.query.addPath("volumes"));
    }

    public Turnovers turnovers() {
        return new Turnovers(this, this.query.addPath("turnovers"));
    }

    public Openpositions openpositions() {
        return new Openpositions(this, this.query.addPath("openpositions"));
    }

}
