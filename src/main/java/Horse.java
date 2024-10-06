import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

import static java.util.Objects.isNull;

public class Horse {

    private final String name;
    private final double speed;
    private double distance;
    private static Logger log = LogManager.getLogger("HorseLogger");

    public Horse(String name, double speed, double distance) {
        if (isNull(name)) {
            log.error("Name is null");
            throw new IllegalArgumentException("Name cannot be null.");
        } else if (name.isBlank()) {
            log.error("Name is blank");
            throw new IllegalArgumentException("Name cannot be blank.");
        }
        if (speed < 0) {
            log.error("Speed is negative");
            throw new IllegalArgumentException("Speed cannot be negative.");
        }
        if (distance < 0) {
            log.error("Distance is negative");
            throw new IllegalArgumentException("Distance cannot be negative.");
        }

        this.name = name;
        this.speed = speed;
        this.distance = distance;
        log.info("Создание Horse, имя [{}], скорость [{}]", name, speed);
    }

    public Horse(String name, double speed) {
        this(name, speed, 0);
    }

    public String getName() {
        return name;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDistance() {
        return distance;
    }

    public void move() {
        distance += speed * getRandomDouble(0.2, 0.9);
    }

    public static double getRandomDouble(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Horse horse = (Horse) o;
        return Double.compare(speed, horse.speed) == 0 && Double.compare(distance, horse.distance) == 0 && Objects.equals(name, horse.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, speed, distance);
    }
}
