package br.com.denisecastro.cielopaylab.core;

import java.util.UUID;

public final class TransactionIdGenerator {

    private TransactionIdGenerator() {
    }

    public static String generate() {
        return UUID.randomUUID().toString();
    }
}
