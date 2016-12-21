package org.fakebuilder.structures;

public class FakeListFilter<T> {

    protected final FakeListBuilder builder;
    protected final int elementsToApply;

    public FakeListFilter(FakeListBuilder builder, int elementsToApply) {
        this.builder = builder;
        this.elementsToApply = elementsToApply;
    }

    public FakeListBuilder<T> with(org.fakebuilder.api.FakeBuilder.ApplyValuesFn applyFn) {
        this.builder.addApplyElement(this.elementsToApply, applyFn);
        return this.builder;
    }
}
