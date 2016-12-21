package baz.codesGeneratorComponent.utils;

import com.google.gson.Gson;
import java.lang.reflect.Type;

public class JSONUtils {

    private final static JSONUtils INSTANCE = new JSONUtils();
    private final Gson gson;

    private JSONUtils() {
        gson = new Gson();
    }

    public static JSONUtils getInstance() {
        return INSTANCE;
    }

    public <T> T JSONToObject(String json, Class<T> classType) {
        return gson.fromJson(json, classType);
    }

    public <T> T[] JSONToArrayList(String json, Type lstType) {
        return gson.fromJson(json, lstType);
    }

    public <T> T JSONToObject(String json, Type objType) {
        return gson.fromJson(json, objType);
    }

    public <T> String ObjectToJSON(T obj) throws Exception {
        return gson.toJson(obj);
    }

}
