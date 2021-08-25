package infratest;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonHelper {

    private static ExclusionStrategy strategy = new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes field) {
            if (field.getName().equals("nodeId")) {
                return true;
            }
            return false;
        }
        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    };

    private static Gson gson = new GsonBuilder()
            .addSerializationExclusionStrategy(strategy)
            .create();

    public static String toJson(Object o) {
        return gson.toJson(o);
    }
}
