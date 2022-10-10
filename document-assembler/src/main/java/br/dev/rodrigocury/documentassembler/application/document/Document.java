package br.dev.rodrigocury.documentassembler.application.document;

import lombok.*;

import java.util.function.Consumer;
import java.util.function.Function;

@Getter
public abstract class Document<B extends Branch<T>, T extends Leaf> {

    protected Branch<T> initialBranch;

    public <R> R produce(Function<Document<B, T>, R> function) {
        return function.apply(this);
    }

    public void consume(Consumer<Document<B, T>> consumer) {
        consumer.accept(this);
    }

}
