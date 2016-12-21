package org.fakebuilder.structures;

public class FakeListSizer<T> {

    private final Class<T> classOfT;

    public FakeListSizer(Class<T> classOfT) {
        this.classOfT = classOfT;
    }

    public FakeListOptions<T> ofSize(int size) {
        return new FakeListOptions<>(this.classOfT, size);
    }
}
