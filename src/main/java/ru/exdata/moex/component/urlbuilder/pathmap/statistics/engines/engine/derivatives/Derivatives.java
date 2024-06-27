package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.engine.derivatives;

import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.engine.Engine;

public final class Derivatives {
    final Engine engine;
    private final Query query;

    public Derivatives(Engine engine, final Query query) {
        this.engine = engine;
        this.query = query;
    }

    public Report_name report_name(final String report_name) {
        if (report_name == null || report_name.isBlank()) {
            throw new IllegalArgumentException(report_name);
        }
        return new Report_name(this, this.query.addPath(report_name));
    }

}
