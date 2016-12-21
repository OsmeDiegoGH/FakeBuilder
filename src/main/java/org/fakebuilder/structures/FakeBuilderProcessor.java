package org.fakebuilder.structures;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.fakebuilder.utils.NumberUtils;
import org.fakebuilder.utils.StringUtils;

public class FakeBuilderProcessor {
    
    protected final NumberUtils numberUtils = NumberUtils.getInstance();
    protected final StringUtils stringUtils = StringUtils.getInstance();

    protected <T> T createNew(Class<T> classOfT) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
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
                default:
                    //class field
                    field.set(instance, createNew(field.getType()));
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
            } else {
                params.add(null);
            }
        }
        return constr.newInstance(params.toArray());
    }
}
