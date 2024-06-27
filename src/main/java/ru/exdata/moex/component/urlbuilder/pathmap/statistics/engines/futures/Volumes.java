package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.futures;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Volumes {
    private final Asset asset;
    private final Query query;

    Volumes(Asset asset, final Query query) {
        this.asset = asset;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/879">Объем торгов для опционной серии</a>
     */
    public Format format() {
        return new Format(asset.assets.options.markets.futures.engines.statistics.iss.issClient.httpClient, asset.assets.options.markets.futures.engines.statistics.iss.issClient.uri, this.query);
    }
}
