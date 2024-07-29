package kr.or.kosa.cmsplusmain.domain.payment;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;
import kr.or.kosa.cmsplusmain.domain.payment.entity.method.PaymentMethod;

import java.io.IOException;

public class PaymentMethodDeserializer extends JsonDeserializer<PaymentMethod> {
    @Override
    public PaymentMethod deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        if (node instanceof TextNode) {
            String text = node.textValue();
            if (text == null || text.isEmpty()) {
                return PaymentMethod.CMS;  // Default value for empty string
            }
            try {
                return PaymentMethod.valueOf(text.toUpperCase());
            } catch (IllegalArgumentException e) {
                return PaymentMethod.CMS;  // Default value for invalid input
            }
        }
        return PaymentMethod.CMS;  // Default value for non-text input
    }
}
