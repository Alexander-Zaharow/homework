package jdbc;

import com.company.Phone;

import java.util.List;

public abstract class AbstractDAO<T extends Phone, K extends Integer> {
    public abstract List findAll();
    public abstract T findEntityById(K id);
    public abstract boolean delete(K id);
    public abstract boolean delete(T entity);
    public abstract boolean create(T entity);
    public abstract T update(T entity);
}

