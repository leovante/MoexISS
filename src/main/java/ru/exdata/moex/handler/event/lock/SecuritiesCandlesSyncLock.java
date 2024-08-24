package ru.exdata.moex.handler.event.lock;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.exdata.moex.utils.lock.SynchronizedResource;

@AllArgsConstructor
@Data
public class SecuritiesCandlesSyncLock implements SynchronizedResource {

    private final String interval;

    @Override
    public String getLockKey() {
        return String.format("securities.candles_sync.%s", interval);
    }
}
