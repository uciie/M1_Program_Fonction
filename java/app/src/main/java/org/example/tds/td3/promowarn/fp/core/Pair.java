package org.example.td3.promowarn.fp.core;

// because we are not using vavr here

public final class Pair<A, B> {
    private final A fst;
    private final B snd;

    public Pair(A a, B b) {
        fst = a;
        snd = b;
    }

    public A fst() {
        return fst;
    }

    public B snd() {
        return snd;
    }

}
