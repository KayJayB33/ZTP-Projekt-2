package pl.edu.pk.ztpprojekt2.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class PriceSerializer extends JsonSerializer<BigDecimal> {
    @Override
    public void serialize(BigDecimal value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        // Using writNumber and removing toString make sure the output is number but not String.
        jgen.writeNumber(value.setScale(2, RoundingMode.HALF_UP));
    }
}
