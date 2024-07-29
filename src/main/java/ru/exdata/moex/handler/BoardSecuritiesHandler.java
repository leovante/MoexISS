package ru.exdata.moex.handler;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import ru.exdata.moex.handler.client.BoardSecuritiesApiClient;

/**
 * Получение названий акций списком с пагинацией.
 */
@Slf4j
@RequiredArgsConstructor
@Singleton
public class BoardSecuritiesHandler {

    private final BoardSecuritiesApiClient boardSecuritiesApiClient;

    public Mono<Object> fetchColumns() {
        return boardSecuritiesApiClient.fetchColumns();
    }

    public Mono<Object> fetchSecurities() {
        return boardSecuritiesApiClient.fetchSecurities();
    }

}
