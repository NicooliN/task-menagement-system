package ru.petapp.taskmenagementsystem.taskmenagment.model;

public enum Priority {
    HIGH("Высокий"),
    MIDDLE("Средний"),
    LOW("Низкий");

    private final String priorityTextDisplay;

    Priority(String text) {
        this.priorityTextDisplay = text;
    }
    
    public String getPriorityTextDisplay() {
        return this.priorityTextDisplay;
    }
}
