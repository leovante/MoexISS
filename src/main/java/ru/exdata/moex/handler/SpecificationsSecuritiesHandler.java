package ru.exdata.moex.handler;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import ru.exdata.moex.db.dao.SecuritiesSpecDao;
import ru.exdata.moex.db.records.rSecuritiesSpec;

/**
 * Получение названий акций списком с пагинацией.
 */
@Slf4j
@RequiredArgsConstructor
@Singleton
public class SpecificationsSecuritiesHandler {

    private final SecuritiesSpecDao securitiesSpecDao;

    public Flux<rSecuritiesSpec> fetchByListingLvl(Integer lvl) {
        validateRequest(lvl);
        return securitiesSpecDao.findByListLvl(lvl);
    }

    private void validateRequest(Integer listing) {
        if (listing == null || listing < 1 || listing > 3) {
            throw new RuntimeException("Ошибка валидатора запроса. listing должен быть == 1,2,3");
        }
    }

}
