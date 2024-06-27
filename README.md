## moex2

### Описание
swagger-ui: http://path-to-stub/swagger-ui.html  

### запуск сервиса в docker локально
- mvn clean install -DskipTests
- docker build -t leovante/moex2 .
- docker compose up (заменить в файле leovante/moex2:latest на moex2)
- Your program changed?
    docker compose down
    docker rmi moex2
    docker build -t moex2 .
    docker compose up
- Invoke only your program inside a container:
    docker run --name moex2 -p 24300:8080 moex2

### запуск локально образа из docker hub
- docker compose up

### тест энпоинтов. Ввести в терминале любой запроос: (все ендпоинты это топики, которые возвращают stream-json)
- при старте приложения заполнится таблица securities списком актуальных акций на мос бирже.
- в таблицу securities_trades сохранятся выполненные ордера текущей торговой сессии.
trades скачивает и в конце продолжает делать запросы пока не появятся новые записи, без остановки.
curl -X GET "http://localhost:24300/iss/engines/stock/markets/shares/securities/sber/trades"
- в таблицу securities_history сохранятся исторические цены. Один день - одна запись.
history скачивает и автоматически останавливается после окончания.
curl -X GET "http://localhost:24300/iss/history/engines/stock/markets/shares/boards/TQBR/securities/sber?from=2024-07-09&till=2024-07-11"