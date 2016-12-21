package org.fakebuilder.structures;

import org.fakebuilder.api.FakeBuilder.ApplyValuesFn;

public class FakeNextListFilter<T> {

    protected final FakeNextListBuilderOptions builder;
    protected final int elementsToApply;

    public FakeNextListFilter(FakeNextListBuilderOptions builder, int elementsToApply) {
        this.builder = builder;
        this.elementsToApply = elementsToApply;
    }

    public FakeNextListBuilderOptions<T> with(ApplyValuesFn applyFn) {
        this.builder.addApplyElement(this.elementsToApply, applyFn);
        return this.builder;
    }
}
