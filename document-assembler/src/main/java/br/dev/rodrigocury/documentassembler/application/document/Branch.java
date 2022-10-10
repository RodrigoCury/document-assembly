package br.dev.rodrigocury.documentassembler.application.document;

import lombok.Getter;

import java.util.List;

@Getter
public abstract class Branch<T extends Leaf> {
    protected List<T> leaves;
    protected Branch<T> nextBranch;
}
