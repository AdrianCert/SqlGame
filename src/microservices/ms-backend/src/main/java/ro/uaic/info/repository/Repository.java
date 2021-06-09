package ro.uaic.info.repository;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T> {
    T getById(Integer id) throws SQLException;
    List<T> getAll();
    T update(T d) throws SQLException;
    T add(T d) throws SQLException;
    void delete(T d) throws SQLException;
}
