package de.lubowiecki;

public class Task {

    private int id;

    private String name;

    private boolean open = true;

    public Task(String name) {
        this.name = name;
    }

    public Task(int id, String name, boolean open) {
        this.id = id;
        this.name = name;
        this.open = open;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public boolean isOpen() {
        return open;
    }

    public void toggleOpen() {
        this.open = !open;
    }

    @Override
    public String toString() {
        return name + " (" + (open ? "offen" : "erledigt")  + ")";
    }
}
