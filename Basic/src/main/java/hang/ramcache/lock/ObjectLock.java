package hang.ramcache.lock;

import com.hang.ramcache.IEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ZhangHang
 * @create 2018-01-25 21:31
 **/
public class ObjectLock extends ReentrantLock implements Comparable<ObjectLock> {
    private static final long serialVersionUID = -1738309259140428174L;
    private static final Class<IEntity> I_ENTITY_CLASS = IEntity.class;
    private static final Logger log = LoggerFactory.getLogger(ObjectLock.class);
    private final Class clz;
    private final Comparable value;
    private final boolean entity;

    public ObjectLock(Object object) {
        this(object, false);
    }

    public ObjectLock(Object object, boolean fair) {
        super(fair);
        this.clz = object.getClass();
        if (object instanceof IEntity) {
            this.value = (Comparable) ((IEntity) object).getId();
        } else {
            this.value = new Integer(System.identityHashCode(object));
        }

        if (I_ENTITY_CLASS.isAssignableFrom(this.clz)) {
            this.entity = true;
        } else {
            this.entity = false;
        }
    }

    public boolean isTie(ObjectLock other) {
        if (this.clz != other.clz) {
            return false;
        } else {
            return this.value.compareTo(other.value) == 0;
        }
    }

    public Class getClz() {
        return this.clz;
    }

    public Comparable getValue() {
        return this.value;
    }

    public boolean isEntity() {
        return this.entity;
    }

    @Override
    public int compareTo(ObjectLock o) {
        if (this.isEntity() && !o.isEntity()) {
            return 1;
        } else if (!this.isEntity() && o.isEntity()) {
            return -1;
        } else if (this.clz != o.clz) {
            if (this.clz.hashCode() < o.clz.hashCode()) {
                return -1;
            } else {
                return this.clz.hashCode() > o.clz.hashCode() ? 1 : this.clz.getName().compareTo(o.clz.getName());
            }
        } else {
            return this.value.compareTo(o.value);
        }
    }

}
