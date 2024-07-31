package kr.or.kosa.cmsplusmain;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RandomGenerator {

	private final Random random;

	public  <T> T getRandomInList(List<T> list) {
		return list.get(random.nextInt(list.size()));
	}


	public String generateRandomAccountNumber() {
		return String.format("%014d", random.nextInt(100000000) + 100000000);
	}

	public String generateRandomCardNumber() {
		return String.format("%016d", random.nextInt(1000000000) + 1000000000);
	}

	// AtomicInteger to store the current number
	private AtomicInteger currentNumber = new AtomicInteger(0);

	// Method to generate the next phone number
	public String generateRandomPhone() {
		int number = currentNumber.getAndIncrement();
		return String.format("010%04d%04d", number / 10000, number % 10000);
	}

	public String generateRandomHomePhone() {
		return String.format("02%04d%04d", random.nextInt(10000), random.nextInt(10000));
	}
}
