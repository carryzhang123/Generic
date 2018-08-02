package hang.ramcache.util;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ZhangHang
 * @create 2018-01-29 14:55
 **/
public class ConcurrentHashSet<E> extends AbstractSet<E> implements Serializable {
    private static final long serialVersionUID = 3563079064703048298L;
protected final ConcurrentHashMap<E,Boolean> map;

    public ConcurrentHashSet() {
        this.map =new ConcurrentHashMap<E, Boolean>();
    }

    public ConcurrentHashSet(int initialCapacity) {
        this.map = new ConcurrentHashMap<E, Boolean>(initialCapacity);
    }

    public ConcurrentHashSet(int initialCapacity,float loadFactory) {
        this.map = new ConcurrentHashMap<E, Boolean>(initialCapacity,loadFactory);
    }

    public ConcurrentHashSet(int initialCapacity,float loadFactor,int concurrentLevel) {
        this.map = new ConcurrentHashMap<E, Boolean>(initialCapacity,loadFactor,concurrentLevel);
    }

    public ConcurrentHashSet(Collection<E> collection) {
        this(collection.size());
        this.addAll(collection);
    }

    @Override
    public Iterator<E> iterator() {
        return this.map.keySet().iterator();
    }

    @Override
    public int size() {
        return this.map.size();
    }

    public boolean contains(Object o){
        return this.map.containsKey(o);
    }

    public boolean add(E o){
        return this.map.put(o,Boolean.TRUE)==null;
    }

    public boolean remove(Object o){
        return this.map.remove(o)!=null;
    }

    public void clear(){
        this.map.clear();
    }
}
