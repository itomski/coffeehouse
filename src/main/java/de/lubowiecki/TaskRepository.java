package de.lubowiecki;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

        try(Connection conn = DbUtils.getConnection(); Statement stmt = conn.createStatement()) {
            final String SQL = "SELECT * FROM " + TABLE;
            stmt.execute(SQL);
            ResultSet results = stmt.getResultSet();
            List<Task> tasks = new ArrayList<>();
            while(results.next()) {
                // Tasks erzeugen und in der Liste ablegen
                tasks.add(populate(results));
            }
            return tasks;
        }
    }

    @Override
    public Task findById(int id) throws SQLException {
        return null;
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
        return false;
    }

    @Override
    public boolean insert(Task task) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Task task) throws SQLException {
        return false;
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
