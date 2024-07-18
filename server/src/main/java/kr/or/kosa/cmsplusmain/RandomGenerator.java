package kr.or.kosa.cmsplusmain;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingStatus;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingType;
import kr.or.kosa.cmsplusmain.domain.payment.entity.Bank;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RandomGenerator {

	private final Random random;

	private final List<Bank> allBanks = Arrays.stream(Bank.values()).toList();
	private final List<BillingStatus> allBillingStatus = Arrays.stream(BillingStatus.values()).toList();
	private final List<BillingType> allBillingType = Arrays.stream(BillingType.values()).toList();

	public  <T> T getRandomInList(List<T> list) {
		return list.get(random.nextInt(list.size()));
	}

	public BillingType getRandomBillingType() {
		return allBillingType.get(random.nextInt(allBillingType.size()));
	}

	public Bank getRandomBank() {
		return allBanks.get(random.nextInt(allBanks.size()));
	}

	public LocalDate generateRandomDate(LocalDate from, LocalDate to) {
		Period period = Period.between(from, to);
		return from
			.plusMonths(random.nextInt(period.getMonths()))
			.plusDays(random.nextInt(period.getDays()));
	}

	public BillingStatus getRandomBillingStatus(LocalDate billingDate) {
		// 결제일이 지난 경우
		// 가능한 상태는 [완납, 미납]
		if (LocalDate.now().isAfter(billingDate)) {
			return getRandomInList(List.of(BillingStatus.PAID, BillingStatus.NON_PAID));
		}
		return getRandomInList(allBillingStatus);
	}

	public String generateRandomInvoiceMessage() {
		String[] messages = {
			null,
			"감사합니다.",
			"이번 달 청구서입니다.",
			"문의사항은 고객센터로 연락주세요.",
			"결제 기한을 지켜주세요."
		};
		return messages[random.nextInt(messages.length)];
	}

	public BillingStatus getRandomBillingStatus() {
		return allBillingStatus.get(random.nextInt(allBillingStatus.size()));
	}

	public String generateRandomAccountNumber() {
		return String.format("%014d", random.nextInt(100000000) + 100000000);
	}

	public String generateRandomCardNumber() {
		return String.format("%016d", random.nextInt(1000000000) + 1000000000);
	}

	public String generateRandomPhone() {
		return String.format("010%04d%04d", random.nextInt(10000), random.nextInt(10000));
	}

	public String generateRandomHomePhone() {
		return String.format("02%04d%04d", random.nextInt(10000), random.nextInt(10000));
	}
}
