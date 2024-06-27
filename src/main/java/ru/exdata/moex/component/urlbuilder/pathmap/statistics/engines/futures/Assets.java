package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.futures;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Assets {
    final Options options;
    private final Query query;

    Assets(Options options, final Query query) {
        this.options = options;
        this.query = query;
    }

    public Asset asset(final String asset) {
        if (asset == null || asset.isBlank()) {
            throw new IllegalArgumentException(asset);
        }
        return new Asset(this, this.query.addPath(asset));
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/873">Опционные серии</a>
     */
    public Format format() {
        return new Format(options.markets.futures.engines.statistics.iss.issClient.httpClient, options.markets.futures.engines.statistics.iss.issClient.uri, this.query);
    }

}
