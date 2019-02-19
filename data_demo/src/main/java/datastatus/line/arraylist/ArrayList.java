package datastatus.line.arraylist;

import java.util.Arrays;

public class ArrayList implements List {

    private Object[] elementData;

    private int size;

    public ArrayList(){
        this(3);
    }

    public ArrayList(int init){
        elementData = new Object[init];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Object get(int i) {
        check(i);
        return elementData[i];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    //判断一个元素存不存在某个ArrayList中，只是判断这个元素的下标存不存在
    @Override
    public boolean contains(Object object) {
        return this.indexOf(object) >= 0;   //时间复杂度O(n)
    }

    @Override
    public int indexOf(Object object) {
        if(object == null){
            for (int i = 0; i < size; i++)
                if (elementData[i]==null)
                    return i;
        }else {
            for (int i = 0; i < size; i++)
                if (object.equals(elementData[i]))
                    return i;
        }
        return -1;
    }

    //插入到第i个位置
    @Override
    public void add(int i, Object object) {
        grow();
        check(i);
        for(int index=size;index>i;index--){
            //后面的元素等于前面的元素
            elementData[index] = elementData[index-1];
        }
        elementData[i] = object;
        size++;
    }

    @Override
    public void add(Object object) {
        grow();
        elementData[size++] = object;
    }

    @Override
    public boolean addBefore(Object before, Object object) {

        return false;
    }

    @Override
    public boolean addAfter(Object after, Object object) {

        return false;
    }

    @Override
    public Object remove(int i) {
        check(i);
        Object oldValue = elementData[i];
        for(;i<size;i++){
            //i后面的元素向前移动
            elementData[i] = elementData[i+1];
        }
        elementData[--size] = null;
        return oldValue;
    }

    @Override
    public boolean remove(Object object) {
        try {
            if (object == null) {
                for (int i = 0; i < size; i++)
                    if (elementData[i] == null)
                        remove(i);
                return true;
            } else {
                for (int i = 0; i < size; i++)
                    if (elementData[i].equals(object))
                        remove(i);
                return true;
            }
        }catch(Exception e) {
            return false;
        }
    }

    @Override
    public Object replace(int i, Object object) {
        check(i);
        Object old = this.get(i);
        elementData[i] = object;
        return old;
    }

    private void grow(){
        if(size == elementData.length){
            int newCapacity = elementData.length*2;
            elementData = Arrays.copyOf(elementData,newCapacity);
        }
    }

    private void check(int i){
        if(i<0 || i>=size){
            throw new MyArrayListException("下标越界:"+i);
        }
    }

}
