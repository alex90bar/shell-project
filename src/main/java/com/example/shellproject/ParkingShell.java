package com.example.shellproject;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

/**
 * ParkingShell
 *
 * @author alex90bar
 */

@ShellComponent
public class ParkingShell {

    private final Map<Integer, Integer> slots = new HashMap<>();

    @ShellMethod(key = "i", value = "Fills parking with free slots. Params (int): small, medium, big.")
    public String init(@ShellOption(value = "s", defaultValue = "10") int small,
        @ShellOption(value = "m", defaultValue = "20") int medium,
        @ShellOption(value = "b", defaultValue = "30") int big) {
        slots.put(1, small);
        slots.put(2, medium);
        slots.put(3, big);
        return MessageFormat.format("Slots: [small: {0}, medium: {1}, big: {2}]", small, medium, big);
    }

    @ShellMethod(key = "a", value = "Adds a car to parking, if there are free slots for car type. Param: carType. Available types: 1 - small, 2 - medium, 3 - big.")
    @ShellMethodAvailability("canAddCar")
    public String addCar(int carType) {
        int freeSlots = slots.get(carType);

        if (freeSlots > 0) {
            slots.put(carType, freeSlots - 1);
            return "Car has been parked successfully";
        }

        return "There's no enough space for parking";
    }

    public Availability canAddCar() {
        int freeSlots = 0;
        for (Entry<Integer, Integer> entry : slots.entrySet()) {
            int value = entry.getValue();
            freeSlots += value;
        }

        return freeSlots == 0 ? Availability.unavailable("No free slots") : Availability.available();
    }

}


