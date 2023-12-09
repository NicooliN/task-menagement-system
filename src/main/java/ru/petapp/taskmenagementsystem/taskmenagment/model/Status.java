package ru.petapp.taskmenagementsystem.taskmenagment.model;

public enum Status {
    HOLD_ON("В ожидании"),
    IN_PROGRESS("В процессе"),
    START("Начато"),
    FINISH("Завершено");

    private final String statusTextDisplay;

    Status(String text) {
        this.statusTextDisplay = text;
    }
    
    public String getStatusTextDisplay() {
        return this.statusTextDisplay;
    }
}
