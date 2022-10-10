package br.dev.rodrigocury.springdocumentprocessor.application.document.interfaces;

import lombok.Getter;

@Getter
public abstract class Leaf {

    protected String text;

    @Override
    public String toString() {
        return text;
    }

}
