package com.bookingsystem.event.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite Pattern — Composite Node
 *
 * يمثل مجموعة من events (زي festival أو week schedule)
 * ممكن يحتوي على EventLeaf أو EventGroup تانية (nested)
 */
public class EventGroup implements EventComponent {

    private final String groupName;
    private final List<EventComponent> children = new ArrayList<>();

    public EventGroup(String groupName) {
        this.groupName = groupName;
    }

    public void add(EventComponent component) {
        children.add(component);
    }

    public void remove(EventComponent component) {
        children.remove(component);
    }

    public List<EventComponent> getChildren() {
        return children;
    }

    @Override
    public String getName() {
        return groupName;
    }

    @Override
    public void display() {
        System.out.println("📁 Group: " + groupName + " (" + children.size() + " events)");
        for (EventComponent child : children) {
            child.display();
        }
    }

    @Override
    public double getTotalRevenuePotential() {
        double total = 0;
        for (EventComponent child : children) {
            total += child.getTotalRevenuePotential();
        }
        return total;
    }
}