package dev.smootheez.minibankapp.util;

import lombok.experimental.*;

import java.time.*;
import java.time.format.*;
import java.util.*;

@UtilityClass
public class TransactionIdGenerator {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS").withZone(ZoneOffset.UTC);

    public String generate(String type) {
        if (type == null) throw new IllegalArgumentException("Transaction type cannot be null");

        String prefix = type.toUpperCase(Locale.ROOT);
        String timestamp = formatter.format(Instant.now());
        String randomSuffix = UUID.randomUUID().toString().substring(0, 6).toUpperCase();

        return String.format("%s-%s-%s", prefix, timestamp, randomSuffix);
    }
}
