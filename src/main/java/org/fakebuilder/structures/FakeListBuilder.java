package org.fakebuilder.structures;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import org.fakebuilder.api.FakeBuilder.ApplyValuesFn;
import org.fakebuilder.entities.ApplyNextOption;

public class FakeListBuilder<T> extends FakeBuilderProcessor {

    protected final int size;
    protected final Class<T> classOfT;
    protected List<ApplyNextOption> applyList;

    public FakeListBuilder(Class<T> classOfT, int size, List<ApplyNextOption> applyList) {
        this.classOfT = classOfT;
        this.size = size;
        this.applyList = applyList;
    }

    public void addApplyElement(int elementsToApply, ApplyValuesFn applyFn) {
        this.applyList.add(new ApplyNextOption(elementsToApply, applyFn));
    }

    public List<T> build() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object[] elements = new Object[this.size];

        int lastCreatedIndex = 0;
        for (ApplyNextOption applyOption : this.applyList) {
            for (int i = 0; i < applyOption.getElementsToApply(); i++) {
                T element = createNew(this.classOfT);
                applyOption.getApplyFn().apply(element);
                elements[lastCreatedIndex++] = element;
            }
        }

        for (; lastCreatedIndex < this.size; lastCreatedIndex++) {
            elements[lastCreatedIndex] = createNew(this.classOfT);
        }
        return (List<T>) Arrays.asList(elements);
    }
}
