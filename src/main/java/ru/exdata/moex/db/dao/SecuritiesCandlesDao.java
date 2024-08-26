package ru.exdata.moex.db.dao;

import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.exdata.moex.db.entity.*;
import ru.exdata.moex.db.repository.*;
import ru.exdata.moex.dto.candles.Row;
import ru.exdata.moex.enums.Interval;
import ru.exdata.moex.mapper.SecuritiesCandlesMapper;

import java.time.LocalDate;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Singleton
public class SecuritiesCandlesDao {

    private final SecuritiesCandlesM1Repository securitiesCandlesM1Repository;
    private final SecuritiesCandlesM10Repository securitiesCandlesM10Repository;
    private final SecuritiesCandlesM60Repository securitiesCandlesM60Repository;
    private final SecuritiesCandlesD1Repository securitiesCandlesD1Repository;
    private final SecuritiesCandlesD7Repository securitiesCandlesD7Repository;
    private final SecuritiesCandlesD31Repository securitiesCandlesD31Repository;

    @Transactional
    public <T, S extends Flux<T>> S findAllByBeginAtAndSecurity(LocalDate start, Integer interval, String security) {
        log.info("fetch candles from db. security: {} interval: {} start: {}", security, interval, start);

        return switch (Objects.requireNonNull(Interval.valueOf(interval))) {
            case M1 -> (S) securitiesCandlesM1Repository.findAllByBeginAtAndSecurity(start, security);
            case M10 -> (S) securitiesCandlesM10Repository.findAllByBeginAtAndSecurity(start, security);
            case M60 -> (S) securitiesCandlesM60Repository.findAllByBeginAtAndSecurity(start, security);
            case D1 -> (S) securitiesCandlesD1Repository.findAllByBeginAtAndSecurity(start, security);
            case D7 -> (S) securitiesCandlesD7Repository.findAllByBeginAtAndSecurity(start, security);
            case D31 -> (S) securitiesCandlesD31Repository.findAllByBeginAtAndSecurity(start, security);
            default -> throw new RuntimeException("Not defined time frame");
        };
    }

    @Transactional
    public <T, S extends Mono<T>> S save(Row row, Integer interval, String security) {
        log.info("save candles to db. security: {} interval: {} start: {} finish: {}",
                security, interval, row.getBegin(), row.getEnd());

        if (row == null || row.getBegin() == null || row.getEnd() == null || security == null) {
            return (S) Mono.empty();
        }

        var begin = row.getBegin();
        var end = row.getEnd();
        return switch (Objects.requireNonNull(Interval.valueOf(interval))) {
            case M1 -> (S) securitiesCandlesM1Repository.findByPk(security, begin, end)
                    .switchIfEmpty(securitiesCandlesM1Repository.save(
                            SecuritiesCandlesMapper.fromArrToEntity(row, security, new SecuritiesCandlesM1())));
            case M10 -> (S) securitiesCandlesM10Repository.findByPk(security, begin, end)
                    .switchIfEmpty(securitiesCandlesM10Repository.save(
                            SecuritiesCandlesMapper.fromArrToEntity(row, security, new SecuritiesCandlesM10())));
            case M60 -> (S) securitiesCandlesM60Repository.findByPk(security, begin, end)
                    .switchIfEmpty(securitiesCandlesM60Repository.save(
                            SecuritiesCandlesMapper.fromArrToEntity(row, security, new SecuritiesCandlesM60())));
            case D1 -> (S) securitiesCandlesD1Repository.findByPk(security, begin, end)
                    .switchIfEmpty(securitiesCandlesD1Repository.save(
                            SecuritiesCandlesMapper.fromArrToEntity(row, security, new SecuritiesCandlesD1())));
            case D7 -> (S) securitiesCandlesD7Repository.findByPk(security, begin, end)
                    .switchIfEmpty(securitiesCandlesD7Repository.save(
                            SecuritiesCandlesMapper.fromArrToEntity(row, security, new SecuritiesCandlesD7())));
            case D31 -> (S) securitiesCandlesD31Repository.findByPk(security, begin, end)
                    .switchIfEmpty(securitiesCandlesD31Repository.save(
                            SecuritiesCandlesMapper.fromArrToEntity(row, security, new SecuritiesCandlesD31())));
        };
    }

    @SneakyThrows
    @Transactional
    public Mono<Long> countByBeginAtAndSecurity(LocalDate start, Integer interval, String security) {
        log.info("fetch isExistByBeginAtAndSecurity from db. security: {} interval: {} start: {}", security, interval, start);

        return Mono.just(interval)
                .map(Interval::valueOf)
                .flatMap(in ->
                        switch (in) {
                            case M1 -> securitiesCandlesM1Repository.count(start, security);
                            case M10 -> securitiesCandlesM10Repository.count(start, security);
                            case M60 -> securitiesCandlesM60Repository.count(start, security);
                            case D1 -> securitiesCandlesD1Repository.count(start, security);
                            case D7 -> securitiesCandlesD7Repository.count(start, security);
                            case D31 -> securitiesCandlesD31Repository.count(start, security);
                        });
    }

}
