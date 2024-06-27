package ru.exdata.moex.component.urlbuilder.pathmap;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Event_id {
    private final Events events;
    private final Query query;

    Event_id(Events events, final Query query) {
        this.events = events;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/194">Контент мероприятия биржи</a>
     */
    public Format format() {
        return new Format(events.iss.issClient.httpClient, events.iss.issClient.uri, this.query);
    }
}
