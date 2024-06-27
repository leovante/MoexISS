package ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.yields;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.history.engines.markets.Market;

public final class Yields {
    final Market market;
    private final Query query;

    public Yields(Market market, final Query query) {
        this.market = market;
        this.query = query;
    }

    public Security security(final String security) {
        if (security == null || security.isBlank()) {
            throw new IllegalArgumentException(security);
        }
        return new Security(this, this.query.addPath(security));
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/791">Получить историю рассчитанных доходностей для всех бумаг на указанном режиме торгов отфильтрованных по дате.</a>
     */
    public Format format() {
        return new Format(market.markets.engine.engines.history.iss.issClient.httpClient, market.markets.engine.engines.history.iss.issClient.uri, this.query);
    }

}
