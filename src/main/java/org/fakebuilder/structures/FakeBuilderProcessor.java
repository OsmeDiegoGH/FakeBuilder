package org.fakebuilder.structures;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.fakebuilder.entities.ApplyNextOption;
import org.fakebuilder.utils.NumberUtils;
import org.fakebuilder.utils.StringUtils;

public class FakeBuilderProcessor {
    /*
    
    TODO: verify support for custom types like GregorianCalendar
    
    */
    
    protected final NumberUtils numberUtils = NumberUtils.getInstance();
    protected final StringUtils stringUtils = StringUtils.getInstance();
    private final int DEFAULT_COLLECTION_SIZE = 10;
    
    public class InvalidTypeException extends Exception{
        public InvalidTypeException(String message) {
            super(message);
        }
    }
    
    public class ConstructorNotFoundException extends Exception{
        public ConstructorNotFoundException(String message) {
            super(message);
        }
    }

    protected <T> T createNew(Class<T> classOfT) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InvalidTypeException, ConstructorNotFoundException {
        return generateRandomValue(classOfT, classOfT.getGenericSuperclass());
    }
    
    private <T> T createNonPrimitiveInstance(Class<T> classOfT) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InvalidTypeException, ConstructorNotFoundException{
        T instance = createEmptyInstance(classOfT);

        for (Field field : classOfT.getDeclaredFields()) {
            if(Modifier.isFinal(field.getModifiers())){
                continue;
            }
            field.setAccessible(true);
            field.set(instance, generateRandomValue(field.getType(), field.getGenericType()));
        }
        return instance;
    }
    
    protected <T> T generateRandomValue(Class<T> classOfT, Type genericType) throws InvalidTypeException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ConstructorNotFoundException{
        String valueType = classOfT.getSimpleName().toLowerCase();
        if (classOfT.isEnum()) {
            valueType = "enum";
        }
        
        switch (valueType) {
            case "int":
            case "integer":
                return (T) new Integer(numberUtils.generateRandomNumber(0, Integer.MAX_VALUE));
            case "boolean":
                return (T) new Boolean((numberUtils.generateRandomNumber(0, Integer.MAX_VALUE) % 2) == 0);
            case "string":
                return (T) stringUtils.generateRandomString(20);
            case "object":
                return (T) new String("Object-" + stringUtils.generateRandomString(20));
            case "date":
                Date randomDate = new Date();
                randomDate.setYear(numberUtils.generateRandomNumber(1900, randomDate.getYear()));
                randomDate.setMonth(numberUtils.generateRandomNumber(1, 12));
                randomDate.setHours(numberUtils.generateRandomNumber(1, 24));
                
                return (T) randomDate;
            case "enum":
                Object[] enumConstants = classOfT.getEnumConstants();
                int randomIndex = numberUtils.generateRandomNumber(0, enumConstants.length);
                return (T) enumConstants[randomIndex];
            case "list":
                ParameterizedType listGenericType = (ParameterizedType)genericType;
                Class<?> listGenericClass = (Class<?>) listGenericType.getActualTypeArguments()[0];
                return (T) createList(listGenericClass, this.DEFAULT_COLLECTION_SIZE, new ArrayList<ApplyNextOption>());
            case "hashmap":
            case "map":
                ParameterizedType mapGenericType = (ParameterizedType)genericType;
                return (T) createMap(
                        (Class<?>) mapGenericType.getActualTypeArguments()[0], 
                        (Class<?>) mapGenericType.getActualTypeArguments()[1]
                );
            default:
                //class type
                return createNonPrimitiveInstance(classOfT);
        }
    }
    
    protected <K, V> HashMap<K, V> createMap(Class<K> classOfKey, Class<V> classOfValue) throws InvalidTypeException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ConstructorNotFoundException{
        HashMap<K, V> generatedMap = new HashMap<>();
        for(int i = 0; i < this.DEFAULT_COLLECTION_SIZE; i++){
            generatedMap.put(
                    generateRandomValue(classOfKey, classOfKey.getGenericSuperclass()), 
                    generateRandomValue(classOfValue, classOfValue.getGenericSuperclass())
            );
        }
        return generatedMap;
    }
    
    protected <T> T createEmptyInstance(Class<T> classOfT) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ConstructorNotFoundException {
        Constructor<T>[] constructorList = (Constructor<T>[])classOfT.getConstructors();
        if(constructorList.length == 0){
            throw new ConstructorNotFoundException("Unable to find constructor for class[" + classOfT.getName() + "]");
        }
        final Constructor<T> constr = constructorList[0];
        
        final List<Object> params = new ArrayList<>();
        for (Class<?> pType : constr.getParameterTypes()) {
            String primitiveTypeName = pType.getSimpleName().toLowerCase();
            if (primitiveTypeName.equals("int") || primitiveTypeName.equals("integer")) {
                params.add(0);
            }else if (primitiveTypeName.equals("boolean") || primitiveTypeName.equals("Boolean")) {
                params.add(false);
            } else {
                params.add(null);
            }
        }
        return constr.newInstance(params.toArray());
    }
    
   public <T> List<T> createList(Class<T> classOfT, int size, List<ApplyNextOption> applyList) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InvalidTypeException, ConstructorNotFoundException {
        Object[] elements = new Object[size];

        int lastCreatedIndex = 0;
        for (ApplyNextOption applyOption : applyList) {
            for (int i = 0; i < applyOption.getElementsToApply(); i++) {
                T element = createNew(classOfT);
                applyOption.getApplyFn().apply(element);
                elements[lastCreatedIndex++] = element;
            }
        }

        for (; lastCreatedIndex < size; lastCreatedIndex++) {
            elements[lastCreatedIndex] = createNew(classOfT);
        }
        return (List<T>) Arrays.asList(elements);
    }
}
