package org.fakebuilder.entities;

import org.fakebuilder.api.FakeBuilder;

public class ApplyNextOption {

    private int elementsToApply;
    private FakeBuilder.ApplyValuesFn applyFn;

    public ApplyNextOption(int elementsToApply, FakeBuilder.ApplyValuesFn applyFn) {
        this.elementsToApply = elementsToApply;
        this.applyFn = applyFn;
    }

    public int getElementsToApply() {
        return elementsToApply;
    }

    public void setElementsToApply(int elementsToApply) {
        this.elementsToApply = elementsToApply;
    }

    public FakeBuilder.ApplyValuesFn getApplyFn() {
        return applyFn;
    }

    public void setApplyFn(FakeBuilder.ApplyValuesFn applyFn) {
        this.applyFn = applyFn;
    }
}
