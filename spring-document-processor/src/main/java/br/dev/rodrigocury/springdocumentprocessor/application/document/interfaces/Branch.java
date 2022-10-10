package br.dev.rodrigocury.springdocumentprocessor.application.document.interfaces;

import lombok.Getter;

import java.util.List;

@Getter
public abstract class Branch<T extends Leaf> {
    protected List<T> leaves;
    protected Branch<T> nextBranch;
}
