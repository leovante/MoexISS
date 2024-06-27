package ru.exdata.moex.component.urlbuilder.pathmap;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;

public final class Events {
    final Iss iss;
    private final Query query;

    Events(Iss iss, final Query query) {
        this.iss = iss;
        this.query = query;
    }

    public Event_id event_id(final String event_id) {
        if (event_id == null || event_id.isBlank()) {
            throw new IllegalArgumentException(event_id);
        }
        return new Event_id(this, this.query.addPath(event_id));
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/193">Мероприятия биржи</a>
     */
    public Format format() {
        return new Format(iss.issClient.httpClient, iss.issClient.uri, this.query);
    }

}
