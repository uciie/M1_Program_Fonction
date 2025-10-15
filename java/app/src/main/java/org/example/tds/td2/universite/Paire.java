package org.example.tds.td2.universite;

public class Paire<T, U> {
    private T fst;
    private U snd;

    public Paire(T fst, U snd) {
        this.fst = fst;
        this.snd = snd;
    }

    public T fst() {
        return fst;
    }

    public U snd() {
        return snd;
    }

    @Override
    public String toString() {
        return "(" + fst + "," + snd + ")";
    }
}
