package de.lubowiecki;

import java.sql.*;
import java.util.List;

public interface Repository<T> {

    public void createTable() throws SQLException;

    public List<T> findAll() throws SQLException;

    public T findById(int id) throws SQLException;

    public boolean delete(T t) throws SQLException;

    public boolean delete(int id) throws SQLException;

    public boolean save(T t) throws SQLException;

    public boolean insert(T t) throws SQLException;

    public boolean update(T t) throws SQLException;

    public T populate(ResultSet rs) throws SQLException;

    public default int executeSQL(String sql) throws SQLException {
        try(Connection conn = DbUtils.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            return stmt.getUpdateCount();
        }
    }
}
