package gson;

import com.google.gson.*;

/**
 * Gson树模型
 */
public class GsonTester {
    public static void main(String args[]) {
        String jsonString =
                "{\"name\":\"Maxsu\", \"age\":26,\"verified\":false,\"marks\": [100,90,85]}";

        JsonParser parser = new JsonParser();
        /**
         * 从json创建树
         */
        JsonElement rootNode = parser.parse(jsonString);

        if (rootNode.isJsonObject()) {
            /**
             * 遍历树模型
             */
            JsonObject details = rootNode.getAsJsonObject();
            JsonElement nameNode = details.get("name");
            System.out.println("Name: " +nameNode.getAsString());

            JsonElement ageNode = details.get("age");
            System.out.println("Age: " + ageNode.getAsInt());

            JsonElement verifiedNode = details.get("verified");
            System.out.println("Verified: " + (verifiedNode.getAsBoolean() ? "Yes":"No"));
            JsonArray marks = details.getAsJsonArray("marks");

            for (int i = 0; i < marks.size(); i++) {
                JsonPrimitive value = marks.get(i).getAsJsonPrimitive();
                System.out.print(value.getAsInt() + " ");
            }
        }
    }
}