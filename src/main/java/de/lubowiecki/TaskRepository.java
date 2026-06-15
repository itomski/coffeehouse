package de.lubowiecki;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class TaskRepository implements Repository<Task> {

    private static final String TABLE = "tasks";

    public TaskRepository() throws SQLException {
        createTable();
    }

    @Override
    public void createTable() throws SQLException {
        final String SQL = "CREATE TABLE IF NOT EXISTS " + TABLE +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, open INTEGER NOT NULL)";

        executeSQL(SQL); // Default Methode aus der Repository-Interface
    }

    @Override
    public List<Task> findAll() throws SQLException {
        return find("SELECT * FROM " + TABLE);
    }

    @Override
    public Task findById(int id) throws SQLException {
        try {
            return find("SELECT * FROM " + TABLE + " WHERE id = " + id).getFirst();
        }
        catch(NoSuchElementException e) {
            return null;
        }
    }

    private List<Task> find(final String SQL) throws SQLException {
        try(Connection conn = DbUtils.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute(SQL);
            ResultSet results = stmt.getResultSet();
            List<Task> tasks = new ArrayList<>();
            while(results.next()) {
                tasks.add(populate(results));
            }
            return tasks;
        }
    }

    @Override
    public boolean delete(Task task) throws SQLException {
        return delete(task.getId());
    }

    @Override
    public boolean delete(int id) throws SQLException {
        final String SQL = "DELETE FROM " + TABLE + " WHERE id = " + id;
        return executeSQL(SQL) > 0;
    }

    @Override
    public boolean save(Task task) throws SQLException {
        if(task.getId() > 0) {
            return update(task);
        }
        return insert(task);
    }

    @Override
    public boolean insert(Task task) throws SQLException {
        final String SQL = "INSERT INTO " + TABLE + " (id, name, open) VALUES(null, ?, ?)";
        return executePreparedSql(SQL, task) > 0;
    }

    @Override
    public boolean update(Task task) throws SQLException {
        final String SQL = "UPDATE " + TABLE + " SET name = ?, open = ? WHERE id = " + task.getId();
        return executePreparedSql(SQL, task) > 0;
    }

    private int executePreparedSql(final String SQL, Task task) throws SQLException {
        try(Connection conn = DbUtils.getConnection(); PreparedStatement stmt = conn.prepareStatement(SQL)) {
            stmt.setString(1, task.getName());
            stmt.setBoolean(2, task.isOpen());
            stmt.execute();
            return stmt.getUpdateCount();
        }
    }

    @Override
    public Task populate(ResultSet rs) throws SQLException {
        return new Task(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getBoolean("open"));
    }
}




//public class TaskRepository {
//
//    public List<Task> findAll() throws SQLException {
//        throw new UnsupportedOperationException("Noch nicht implementiert");
//    }
//
//    public Task findById(int id) throws SQLException {
//        throw new UnsupportedOperationException("Noch nicht implementiert");
//    }
//
//    public boolean delete(Task task) throws SQLException {
//        return delete(task.getId());
//    }
//
//    public boolean delete(int id) throws SQLException {
//        throw new UnsupportedOperationException("Noch nicht implementiert!");
//    }
//
//    public boolean save(Task task) throws SQLException {
//        if(task.getId() > 0) {
//            return update(task);
//        }
//        return insert(task);
//    }
//
//    private boolean insert(Task task) throws SQLException {
//        throw new UnsupportedOperationException("Noch nicht implementiert!");
//    }
//
//    private boolean update(Task task) throws SQLException {
//        throw new UnsupportedOperationException("Noch nicht implementiert!");
//    }
//}
