package error;

import org.apache.derby.impl.store.access.heap.Heap;

import java.util.ArrayList;

/**
 * 堆溢出：(OOM
 * )
 * java.lang.OutOfMemoryError
 */
public class HeapError {

    public static void main(String[] args) {
        ArrayList list=new ArrayList();
        while(true) {
            list.add(new Heap());

        }

    }

}
