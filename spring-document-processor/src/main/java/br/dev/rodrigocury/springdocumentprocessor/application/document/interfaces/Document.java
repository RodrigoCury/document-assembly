package br.dev.rodrigocury.springdocumentprocessor.application.document.interfaces;

import lombok.Getter;

import java.util.function.Consumer;
import java.util.function.Function;

@Getter
public abstract class Document<B extends Branch<L>, L extends Leaf> {

    protected B initialBranch;

    public <R> R produce(Function<Document<B, L>, R> function) {
        return function.apply(this);
    }

    public void consume(Consumer<Document<B, L>> consumer) {
        consumer.accept(this);
    }
}
