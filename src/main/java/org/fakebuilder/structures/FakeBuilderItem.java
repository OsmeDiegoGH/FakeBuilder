package org.fakebuilder.structures;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class FakeBuilderItem<T> extends FakeBuilderProcessor {

    private final Class<T> classOfT;
    private final List<org.fakebuilder.api.FakeBuilder.ApplyValuesFn> applyList = new ArrayList();

    public FakeBuilderItem(Class<T> classOfT) {
        this.classOfT = classOfT;
    }

    public FakeBuilderItem<T> with(org.fakebuilder.api.FakeBuilder.ApplyValuesFn<T> applyFn) {
        applyList.add(applyFn);
        return this;
    }

    public T build() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InvalidTypeException, ConstructorNotFoundException {
        T element = createNew(this.classOfT);
        for (org.fakebuilder.api.FakeBuilder.ApplyValuesFn applyFn : this.applyList) {
            applyFn.apply(element);
        }
        return element;
    }
}
