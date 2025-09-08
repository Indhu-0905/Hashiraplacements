import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.JSONObject;
import org.json.JSONTokener;

public class Hashira {

    // Convert a string value from given base to base 10
    public static long convertToBase10(String value, int base) {
        return Long.parseLong(value, base);
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java -cp .:lib/json.jar Hashira input.json");
            return;
        }

        String filePath = args[0];

        try {
            FileReader reader = new FileReader(filePath);
            JSONTokener tokener = new JSONTokener(reader);
            JSONObject json = new JSONObject(tokener);

            JSONObject keys = json.getJSONObject("keys");
            int n = keys.getInt("n");
            int k = keys.getInt("k");

            System.out.println("Total roots (n): " + n);
            System.out.println("Minimum roots required (k): " + k);
            System.out.println("Roots converted to base 10:");

            Iterator<String> keysIterator = json.keys();
            while (keysIterator.hasNext()) {
                String key = keysIterator.next();
                if (key.equals("keys")) {
                    continue;
                }

                JSONObject root = json.getJSONObject(key);
                int base = Integer.parseInt(root.getString("base"));
                String value = root.getString("value");

                long base10value = convertToBase10(value, base);

                System.out.println("Root " + key + ": base " + base + ", value " + value 
                    + " -> base 10 = " + base10value);
            }

            reader.close();

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error parsing JSON or converting values: " + e.getMessage());
        }
    }
}
