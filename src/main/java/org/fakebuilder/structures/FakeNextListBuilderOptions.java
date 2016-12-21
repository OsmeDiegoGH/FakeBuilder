package org.fakebuilder.structures;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.fakebuilder.entities.ApplyNextOption;
import org.fakebuilder.entities.InvalidIndexException;

public class FakeNextListBuilderOptions<T> extends FakeListBuilder<T> {

    public FakeNextListBuilderOptions(Class classOfT, int size) {
        super(classOfT, size, new ArrayList<ApplyNextOption>());
    }

    public FakeNextListBuilderOptions(Class classOfT, int size, List<ApplyNextOption> applyList) {
        super(classOfT, size, applyList);
    }

    public FakeNextListFilter<T> theNext(int nextIndex) throws InvalidIndexException {
        if (nextIndex > this.size || nextIndex < 1) {
            throw new InvalidIndexException("Next index is out of range");
        }
        if ((getSumOfApplyIndex() + nextIndex) > this.size) {
            throw new InvalidIndexException("The sum of apply indexex is out of range");
        }

        return new FakeNextListFilter(this, nextIndex);
    }

    public FakeListFilter<T> theLast(int lastIndex) throws InvalidIndexException {
        if (lastIndex > this.size || lastIndex < 1) {
            throw new InvalidIndexException("Last index is out of range");
        }
        int sumOfApplyIndexes = getSumOfApplyIndex();
        if ((sumOfApplyIndexes + lastIndex) > this.size) {
            throw new InvalidIndexException("The sum of apply indexex is out of range");
        }

        FakeListBuilder builder = new FakeListBuilder<>(this.classOfT, this.size, this.applyList);

        if ((sumOfApplyIndexes + lastIndex) < this.size) {
            //set a fake nextApplyFn to do nothing on the rest elements
            builder.addApplyElement(this.size - (sumOfApplyIndexes + lastIndex), new org.fakebuilder.api.FakeBuilder.ApplyValuesFn<T>() {
                @Override
                public void apply(T record) {
                }
            });
        }

        return new FakeListFilter<>(builder, lastIndex);
    }

    public int getSumOfApplyIndex() throws InvalidIndexException {
        int countNextIndexes = 0;
        for (ApplyNextOption applyOption : this.applyList) {
            countNextIndexes += applyOption.getElementsToApply();
        }
        return countNextIndexes;
    }

    @Override
    public List<T> build() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object[] elements = new Object[this.size];

        int lastCreatedIndex = 0;
        for (ApplyNextOption applyOption : this.applyList) {
            int elementsToApply = applyOption.getElementsToApply();
            for (int i = 0; i < elementsToApply; i++) {
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
