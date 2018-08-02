package hang.ramcache.orm.impl;

import com.hang.ramcache.IEntity;
import com.hang.ramcache.orm.Accessor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ZhangHang
 * @create 2018-01-27 17:15
 **/
public class HibernateAccessor extends HibernateDaoSupport implements Accessor {
    public HibernateAccessor() {
    }

    @Override
    public <PK extends Serializable, T extends IEntity> T load(Class<T> clz, PK id) {
        return (T) this.getHibernateTemplate().get(clz, id);
    }

    @Override
    public <PK extends Serializable, T extends IEntity> PK save(Class<T> clz, T entity) {
        return (PK) this.getHibernateTemplate().save(entity);
    }

    @Override
    public <PK extends Serializable, T extends IEntity> void remove(Class<T> clz, PK id) {
        T entity = this.load(clz, id);
        if (entity != null) {
            this.getHibernateTemplate().delete(entity);
        }
    }

    @Override
    public <PK extends Serializable, T extends IEntity> void update(Class<T> clz, T entity) {
        this.getHibernateTemplate().update(entity);
    }

    @Override
    public <PK extends Serializable, T extends IEntity> void saveOrUpdate(Class<T> clz, T entity) {
        this.getHibernateTemplate().saveOrUpdate(entity);
    }

    @Override
    public <PK extends Serializable, T extends IEntity> void batchSave(final List<T> entitys) {
        this.getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session session) throws HibernateException, SQLException {
                int size = entitys.size();
                session.beginTransaction();

                for (int i = 0; i < size; ++i) {
                    T entity = entitys.get(i);
                    session.save(entity);
                    if ((i + 1) % 50 == 0) {
                        session.flush();
                        session.clear();
                    }
                }
                session.flush();
                session.clear();
                session.getTransaction().commit();
                return size;
            }
        });
    }

    @Override
    public <PK extends Serializable, T extends IEntity> void batchUpdate(final List<T> entitys) {
        this.getHibernateTemplate().execute(new HibernateCallback<Integer>() {
            @Override
            public Integer doInHibernate(Session session) throws HibernateException, SQLException {
                int size = entitys.size();
                session.beginTransaction();

                for (int i = 0; i < size; i++) {
                    T entity = entitys.get(i);
                    session.update(entity);
                    if ((i + 1) % 50 == 0) {
                        session.flush();
                        session.clear();
                    }
                }
                session.flush();
                session.clear();
                session.getTransaction().commit();
                return size;
            }
        });
    }
}
