import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
public class HorseTest {

	private static Horse horse;
	@BeforeAll
	public static void init() {
		horse = new Horse("SomeName", 2d, 2d);
	}

	@ParameterizedTest
	@NullSource
	public void nullNameConstructor(String name) {
		double speed = 0d; //Non-valid value of speed

		IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
				() -> new Horse(name, speed)
		);
		Assertions.assertEquals("Name cannot be null.", e.getMessage());
	}

	@ParameterizedTest
	@ValueSource(strings = {"", " ", "\t"})
	public void blankNameConstructor(String name) {
		double speed = 0d; //Non-valid value of speed

		IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
				() -> new Horse(name, speed)
		);
		Assertions.assertEquals("Name cannot be blank.", e.getMessage());
	}

	@ParameterizedTest
	@ValueSource(doubles = -1)
	public void negativeSpeedConstructor(double speed) {
		String name = "SomeName";

		IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
				() -> new Horse(name, speed)
		);
		Assertions.assertEquals( "Speed cannot be negative.", e.getMessage());
	}

	@ParameterizedTest
	@ValueSource(doubles = -1)
	public void negativeDistanceConstructor(double distance) {
		String name = "SomeName";
		double speed = 0d;

		IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
				() -> new Horse(name, speed, distance)
		);
		Assertions.assertEquals("Distance cannot be negative.", e.getMessage());
	}

	@ParameterizedTest
	@ValueSource(strings = "SomeName")
	public void getName(String name) {
		double speed = 0d;
		Horse horse = new Horse(name, speed);
		Assertions.assertEquals("SomeName", horse.getName());
	}

	@ParameterizedTest
	@ValueSource(doubles = 1d)
	public void getSpeed(double speed) {
		String name = "SomeName";
		Horse horse = new Horse(name, speed);
		Assertions.assertEquals(1d, horse.getSpeed());
	}

	@ParameterizedTest
	@ValueSource(doubles = 1d)
	public void getDistance(double distance) {
		String name = "SomeName";
		double speed = 0d;
		Horse horseWithThreeArgs = new Horse(name, speed, distance);
		Horse horseWithTwoArgs = new Horse(name, speed);
		Assertions.assertAll(
				() -> Assertions.assertEquals(1d, horseWithThreeArgs.getDistance()),
				() -> Assertions.assertEquals(0d, horseWithTwoArgs.getDistance())
		);
	}

	@Test
	public void verifyGetRandomDoubleInMove() {
		try (MockedStatic<Horse> mockedStaticHorse = mockStatic(Horse.class)) {
			horse.move();
			mockedStaticHorse.verify(() -> Horse.getRandomDouble(0.2, 0.9));
		}
	}

	@Test
	public void move() {
		try (MockedStatic<Horse> mockedStaticHorse = mockStatic(Horse.class)) {
			mockedStaticHorse.when(() -> Horse.getRandomDouble(anyDouble(), anyDouble())).thenReturn(0.5);
			double expected = horse.getDistance() + horse.getSpeed() * Horse.getRandomDouble(0.2, 0.9);
			horse.move();
			Assertions.assertEquals(expected, horse.getDistance());
		}
	}
}
