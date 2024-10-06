import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class HippodromeTest {

	@ParameterizedTest
	@NullSource
	public void nullConstructor(List<Horse> horseList) {
		IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
				() -> new Hippodrome(horseList)
		);
		Assertions.assertEquals("Horses cannot be null.", e.getMessage());
	}

	@ParameterizedTest
	@EmptySource
	public void emptyConstructor(List<Horse> horseList) {
		IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
				() -> new Hippodrome(horseList)
		);
		Assertions.assertEquals("Horses cannot be empty.", e.getMessage());
	}

	@ParameterizedTest
	@MethodSource("horseListStream")
	public void getHorses(List<Horse> horseList) {
		Hippodrome hippodrome = new Hippodrome(horseList);
		List<Horse> actual = hippodrome.getHorses();
		Assertions.assertEquals(horseList, actual);
	}

	@ParameterizedTest
	@MethodSource("mockedHorseListStream")
	public void move(List<Horse> horseList) {
		Hippodrome hippodrome = new Hippodrome(horseList);
		for (int i = 0; i < hippodrome.getHorses().size(); i++) {
			hippodrome.getHorses().get(i).move();
			Mockito.verify(hippodrome.getHorses().get(i), Mockito.times(1)).move();
		}
	}

	@ParameterizedTest
	@MethodSource("horseListWithDistance")
	public void getWinner(List<Horse> horseList) {
		Horse expected = horseList.stream().max(Comparator.comparing(Horse::getDistance)).get();
		Hippodrome hippodrome = new Hippodrome(horseList);
		Horse actual = hippodrome.getWinner();
		Assertions.assertEquals(expected, actual);
	}

	private static Stream<List<Horse>> horseListStream() {
		int horseName = 0;
		List<Horse> horses = new ArrayList<>(30);
		for (int i = 0; i < 30; i++) {
			horseName++;
			horses.add(new Horse(String.valueOf(horseName), 1d));
		}
		return Stream.of(horses);
	}

	private static Stream<List<Horse>> mockedHorseListStream() {
		List<Horse> mockedHorseList = new ArrayList<>(50);
		for (int i = 0; i < 50; i++) {
			mockedHorseList.add(Mockito.mock(Horse.class));
		}
		return Stream.of(mockedHorseList);
	}

	private static Stream<List<Horse>> horseListWithDistance() {
		int horseName = 0;
		double distance = 0;
		List<Horse> horses = new ArrayList<>(30);
		for (int i = 0; i < 30; i++) {
			horseName++;
			distance++;
			horses.add(new Horse(String.valueOf(horseName), 1d, distance));
		}
		return Stream.of(horses);
	}
}
