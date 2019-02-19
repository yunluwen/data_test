package datastatus.line;

import datastatus.line.arraylist.ArrayList;

public class TestArrayList {

    public static void main(String[] args) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);
        arrayList.add(2,9);
        System.out.println(arrayList.isEmpty());
        System.out.println(arrayList.get(2));
        System.out.println(arrayList.remove(3));
        System.out.println(arrayList.get(3));
        //System.out.println(arrayList.size());

        //java.util.ArrayList;
    }
}
