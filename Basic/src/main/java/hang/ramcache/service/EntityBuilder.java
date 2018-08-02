package hang.ramcache.service;

import com.hang.ramcache.IEntity;

import java.io.Serializable;

public interface EntityBuilder<PK extends Comparable<PK> & Serializable,T extends IEntity<PK>> {
    T newInstance(PK var1);
}
