package ru.exdata.moex.component.urlbuilder.pathmap.engines.market;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.engines.Markets;

public final class Market {
    public final Markets markets;
    private final Query query;

    public Market(Markets markets, final Query query) {
        this.markets = markets;
        this.query = query;
    }

    public Trades trades() {
        return new Trades(this, this.query.addPath("trades"));
    }

    public Boards boards() {
        return new Boards(this, this.query.addPath("boards"));
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/44">Получить описание: словарь доступных режимов торгов, описание полей публикуемых таблиц данных и т.д. например: https://iss.moex.com/iss/engines/stock/markets/shares.xml</a>
     */
    public Format format() {
        return new Format(markets.engine.engines.iss.issClient.httpClient, markets.engine.engines.iss.issClient.uri, this.query);
    }

    public Boardgroups boardgroups() {
        return new Boardgroups(this, this.query.addPath("boardgroups"));
    }

    public Securities securities() {
        return new Securities(this, this.query.addPath("securities"));
    }

    public Secstats secstats() {
        return new Secstats(this, this.query.addPath("secstats"));
    }

    public Turnovers turnovers() {
        return new Turnovers(this, this.query.addPath("turnovers"));
    }

    public Orderbook orderbook() {
        return new Orderbook(this, this.query.addPath("orderbook"));
    }

}
