package br.dev.rodrigocury.documentassembler.application.document;

import lombok.*;

@Getter
public abstract class Leaf {

    protected String text;

    @Override
    public String toString() {
        return text;
    }

}
