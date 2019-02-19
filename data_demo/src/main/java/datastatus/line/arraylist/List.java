package datastatus.line.arraylist;

public interface List {

    int size();
    Object get(int i);
    boolean isEmpty();
    boolean contains(Object object);
    int indexOf(Object object);
    void add(int i,Object object);
    void add(Object object);
    boolean addBefore(Object before,Object object);
    boolean addAfter(Object after,Object object);
    Object remove(int i);
    boolean remove(Object object);
    Object replace(int i,Object object);

}
