package org.fakebuilder.structures;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.fakebuilder.entities.ApplyNextOption;
import org.fakebuilder.utils.NumberUtils;
import org.fakebuilder.utils.StringUtils;

public class FakeBuilderProcessor {
    
    protected final NumberUtils numberUtils = NumberUtils.getInstance();
    protected final StringUtils stringUtils = StringUtils.getInstance();
    private final int DEFAULT_LIST_SIZE = 10;
    
    public class InvalidTypeException extends Exception{
        public InvalidTypeException(String message) {
            super(message);
        }
    }

    protected <T> T createNew(Class<T> classOfT) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InvalidTypeException {
        String classOfTSimpleName = classOfT.getSimpleName().toLowerCase();
        
        switch (classOfTSimpleName) {
            case "int":
            case "integer":
                return (T) new Integer(numberUtils.generateRandomNumber(0, Integer.MAX_VALUE));
            case "boolean":
                return (T) new Boolean((numberUtils.generateRandomNumber(0, Integer.MAX_VALUE) % 2) == 0);
            case "string":
                return (T) stringUtils.generateRandomString(20);
            case "object":
                return (T) new String("Object-" + stringUtils.generateRandomString(20));
            case "enum":
                Object[] enumConstants = classOfT.getEnumConstants();
                int randomIndex = numberUtils.generateRandomNumber(0, enumConstants.length);
                return (T) enumConstants[randomIndex];
            case "list":
                throw new InvalidTypeException("Invalid tpye List");
            default:
                //class type
                return createNonPrimitiveInstance(classOfT);
        }
    }
    
    private <T> T createNonPrimitiveInstance(Class<T> classOfT) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InvalidTypeException{
        T instance = createEmptyInstance(classOfT);

        for (Field field : classOfT.getDeclaredFields()) {
            field.setAccessible(true);

            String primitiveFieldType = field.getType().getSimpleName().toLowerCase();
            if (field.getType().isEnum()) {
                primitiveFieldType = "enum";
            }

            switch (primitiveFieldType) {
                case "int":
                case "integer":
                    field.set(instance, numberUtils.generateRandomNumber(0, Integer.MAX_VALUE));
                    break;
                case "boolean":
                    field.set(instance, (numberUtils.generateRandomNumber(0, Integer.MAX_VALUE) % 2) == 0);
                    break;
                case "string":
                    field.set(instance, stringUtils.generateRandomString(20));
                    break;
                case "object":
                    field.set(instance, "Object-" + stringUtils.generateRandomString(20));
                    break;
                case "enum":
                    Object[] enumConstants = field.getType().getEnumConstants();
                    int randomIndex = numberUtils.generateRandomNumber(0, enumConstants.length);
                    field.set(instance, enumConstants[randomIndex]);
                    break;
                case "list":
                    ParameterizedType listGenericType = (ParameterizedType)field.getGenericType();
                    Class<?> listGenericClass = (Class<?>) listGenericType.getActualTypeArguments()[0];
                    field.set(instance, createList(listGenericClass, this.DEFAULT_LIST_SIZE, new ArrayList<ApplyNextOption>()));
                    break;
                default:
                    //class field
                    field.set(instance, createNonPrimitiveInstance(field.getType()));
                    break;
            }
        }
        return instance;
    }

    protected <T> T createEmptyInstance(Class<T> classOfT) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        // Create instance of the given class
        final Constructor<T> constr = (Constructor<T>) classOfT.getConstructors()[0];
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
    
   public <T> List<T> createList(Class<T> classOfT, int size, List<ApplyNextOption> applyList) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InvalidTypeException {
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
