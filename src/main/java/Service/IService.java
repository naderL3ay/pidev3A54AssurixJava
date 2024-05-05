package Service;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {
    public void add(T t) throws SQLException;

    public void update(T t) throws SQLException;

    public void delete(T t) throws SQLException;

    public T findById(int id) throws SQLException;

    public List<T> ReadAll() throws SQLException;
}
