package org.fakebuilder.structures;

import java.util.ArrayList;
import org.fakebuilder.api.FakeBuilder;
import org.fakebuilder.entities.ApplyNextOption;
import org.fakebuilder.entities.InvalidIndexException;

public class FakeListOptions<T> {

    private final int size;
    private final Class<T> classOfT;

    public FakeListOptions(Class<T> classOfT, int size) {
        this.classOfT = classOfT;
        this.size = size;
    }

    public FakeAllListBuilderFilter<T> all() {
        return new FakeAllListBuilderFilter(this.classOfT, this.size);
    }

    public FakeNextListFilter<T> theFirst(int firstIndex) throws InvalidIndexException {
        if (firstIndex > this.size || firstIndex < 1) {
            throw new InvalidIndexException("First index is out of range");
        }
        return new FakeNextListFilter(new FakeNextListBuilderOptions<>(this.classOfT, this.size), firstIndex);
    }

    public FakeListFilter<T> theLast(int lastIndex) throws InvalidIndexException {
        if (lastIndex > this.size || lastIndex < 1) {
            throw new InvalidIndexException("Last index is out of range");
        }

        FakeListBuilder builder = new FakeListBuilder<>(this.classOfT, this.size, new ArrayList<ApplyNextOption>());

        //set a fake nextApplyFn to do nothing on the first elements
        builder.addApplyElement(this.size - lastIndex, new FakeBuilder.ApplyValuesFn<T>() {
            @Override
            public void apply(T record) {
            }
        });

        return new FakeListFilter<>(builder, lastIndex);
    }
}
