package org.fakebuilder.structures;

import java.util.ArrayList;
import org.fakebuilder.entities.ApplyNextOption;

public class FakeAllListBuilderFilter<T> extends FakeListBuilder<T> {

    public FakeAllListBuilderFilter(Class<T> classOfT, int size) {
        super(classOfT, size, new ArrayList<ApplyNextOption>());
    }

    public FakeListBuilder<T> with(org.fakebuilder.api.FakeBuilder.ApplyValuesFn applyFn) {
        this.addApplyElement(this.size, applyFn);
        return new FakeListBuilder<>(this.classOfT, this.size, this.applyList);
    }
}
