package org.fakebuilder.api;

import org.fakebuilder.structures.FakeBuilderItem;
import org.fakebuilder.structures.FakeListSizer;

public class FakeBuilder{
      
    public static abstract class ApplyValuesFn<T>{
        public abstract void apply(T record);
    }
    
    public <T> FakeListSizer<T> createList(Class<T> classOfT){
        return new FakeListSizer<>(classOfT);
    }  
    
    public <T> FakeBuilderItem<T> createNew(Class<T> classOfT){
        return new FakeBuilderItem<>(classOfT);
    }
}
