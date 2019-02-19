package base;

import java.util.HashMap;
import java.util.Map;

public class MapTest {

    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        map.put("a","bb");
        map.put("s","jjj");
        System.out.println(map.size());
    }
}
