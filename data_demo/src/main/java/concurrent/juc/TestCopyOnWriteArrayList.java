package concurrent.juc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * CopyOnWriteArrayList/CopyOnWriteArraySet : "写入并复制"
 * 注意:添加操作多时，效率低，因为每次添加时，都会进行复制，开销非常的大
 * 并发迭代操作多时可以选择
 */
public class TestCopyOnWriteArrayList {

    public static void main(String[] args) {
        HelloThread helloThread = new HelloThread();
        for(int i=0;i<3;i++){
            new Thread(helloThread).start();
        }
    }

}

class HelloThread implements Runnable {

//    private static List<String> list = Collections.synchronizedList(new ArrayList<>());

    //每次写入时都会复制一份
    //不适合添加操作，适合迭代
    private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

    static {
        list.add("AA");
        list.add("BB");
        list.add("CC");
    }

    @Override
    public void run() {
        Iterator iterator = list.iterator();
        while(iterator.hasNext()){
            System.out.println("Thread-"+Thread.currentThread().getId()+"\t"+iterator.next());

            //同步容器执行这个操作会java.util.ConcurrentModificationException
            list.add("DD");
        }
    }


}