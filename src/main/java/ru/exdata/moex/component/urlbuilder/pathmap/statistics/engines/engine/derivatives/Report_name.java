package ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.engine.derivatives;

import ru.exdata.moex.component.urlbuilder.Format;
import ru.exdata.moex.component.urlbuilder.Query;
import ru.exdata.moex.component.urlbuilder.pathmap.statistics.engines.engine.derivatives.Derivatives;

public final class Report_name {
    private final Derivatives derivatives;
    private final Query query;

    Report_name(Derivatives derivatives, final Query query) {
        this.derivatives = derivatives;
        this.query = query;
    }

    /**
     * <a href="https://iss.moex.com/iss/reference/219">Еженедельные отчеты по валютным деривативам: numtrades - информация о количестве договоров по инструментам, являющимся производными финансовыми инструментами (по валютным парам) participants - информация о количестве лиц, имеющих открытые позиции по инструментам, являющимся производными финансовыми инструментами (по валютным парам) openpositions - информация об открытых позициях по инструментам, являющимся производными финансовыми инструментами (по валютным парам) expirationparticipants - информация о количестве лиц, имеющих открытые позиции по договорам, являющимся производными финансовыми инструментами (по срокам экспирации) expirationopenpositions - информация об объеме открытых позиций по договорам, являющимся производными финансовыми инструментами (по срокам экспирации)</a>
     */
    public Format format() {
        return new Format(derivatives.engine.engines.statistics.iss.issClient.httpClient, derivatives.engine.engines.statistics.iss.issClient.uri, this.query);
    }
}
