package de.lubowiecki;

public class Task {

    private String name;

    private boolean open = true;

    public Task(String name) {
        this.name = name;
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
