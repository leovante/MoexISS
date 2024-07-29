package ru.exdata.moex.dto.candles;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Serdeable
@Data
@JsonIgnoreProperties(ignoreUnknown = false)
public class Row {

    @JacksonXmlProperty(isAttribute = true, localName = "open")
    private Double open;
    @JacksonXmlProperty(isAttribute = true, localName = "close")
    private Double close;
    @JacksonXmlProperty(isAttribute = true, localName = "high")
    private Double high;
    @JacksonXmlProperty(isAttribute = true, localName = "low")
    private Double low;
    @JacksonXmlProperty(isAttribute = true, localName = "value")
    private Double value;
    @JacksonXmlProperty(isAttribute = true, localName = "volume")
    private Double volume;
    @JacksonXmlProperty(isAttribute = true, localName = "begin")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime begin;
    @JacksonXmlProperty(isAttribute = true, localName = "end")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end;

}
