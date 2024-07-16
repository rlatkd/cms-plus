package kr.or.kosa.cmsplusmain.domain.statics.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.kosa.cmsplusmain.domain.billing.dto.BillingProductRes;
import kr.or.kosa.cmsplusmain.domain.billing.entity.Billing;
import kr.or.kosa.cmsplusmain.domain.billing.entity.BillingStatus;
import kr.or.kosa.cmsplusmain.domain.statics.dto.DayBillingDetailRes;
import kr.or.kosa.cmsplusmain.domain.statics.dto.DayBillingRes;
import kr.or.kosa.cmsplusmain.domain.statics.dto.RecentFiveContractRes;
import kr.or.kosa.cmsplusmain.domain.statics.dto.StatInfoRes;
import kr.or.kosa.cmsplusmain.domain.statics.dto.TopFiveMemberRes;
import kr.or.kosa.cmsplusmain.domain.statics.dto.TopInfoRes;
import kr.or.kosa.cmsplusmain.domain.statics.dto.query.BillingStatQueryRes;
import kr.or.kosa.cmsplusmain.domain.statics.dto.query.ContractStatQueryRes;
import kr.or.kosa.cmsplusmain.domain.statics.dto.query.MemberStatQueryRes;
import kr.or.kosa.cmsplusmain.domain.statics.dto.MonthBillingInfoRes;
import kr.or.kosa.cmsplusmain.domain.statics.dto.query.MonthBillingQueryRes;
import kr.or.kosa.cmsplusmain.domain.statics.dto.query.RecentFiveContractQueryRes;
import kr.or.kosa.cmsplusmain.domain.statics.dto.query.TopFiveMemberQueryRes;
import kr.or.kosa.cmsplusmain.domain.statics.repository.StatRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatService {

	private final StatRepository statRepository;

	public StatInfoRes getStatInfo(Long vendorId, int year, int month) {
		MemberStatQueryRes memberStat = statRepository.findMemberStat(vendorId, year, month);
		ContractStatQueryRes contractStat = statRepository.findContractStat(vendorId, year, month);
		BillingStatQueryRes billingStat = statRepository.findBillingStat(vendorId);

		Double memberGrowth = getMemberGrowth(vendorId, year, month);
		Double billingPriceGrowth = getBillingPriceGrowth(vendorId, year, month);

		return new StatInfoRes(
			billingStat.getTotalBillingPrice(),
			billingStat.getTotalPaidPrice(),
			billingStat.getTotalNotPaidPrice(),
			contractStat.getTotalContractCount(),
			contractStat.getNewContractCount(),
			contractStat.getExpectedToExpiredCount(),
			memberStat.getTotalMemberCount(),
			memberStat.getNewMemberCount(),
			memberStat.getActiveMemberCount(),
			memberGrowth, billingPriceGrowth);
	}

	private Double getMemberGrowth(Long vendorId, int year, int month) {
		int lastMonth;
		int lastYear;

		if (month == 1) {
			lastYear = year - 1;
			lastMonth = 12;
		} else {
			lastYear = year;
			lastMonth = month - 1;
		}

		Long curMonthMemberCount = statRepository.countMemberEnrollmentsByMonth(vendorId, year, month);
		Long prevMonthMemberCount = statRepository.countMemberEnrollmentsByMonth(vendorId, lastYear, lastMonth);

		if (prevMonthMemberCount == 0) {
			return 0.0;
		}
		return ((double) (curMonthMemberCount - prevMonthMemberCount) / prevMonthMemberCount) * 100;
	}

	private Double getBillingPriceGrowth(Long vendorId, int year, int month) {
		int lastMonth;
		int lastYear;

		if (month == 1) {
			lastYear = year - 1;
			lastMonth = 12;
		} else {
			lastYear = year;
			lastMonth = month - 1;
		}

		Long curMonthBillingPrice = statRepository.calcBillingPriceByMonth(vendorId, month, year);
		Long prevMonthBillingPrice = statRepository.calcBillingPriceByMonth(vendorId, lastMonth, lastYear);

		if (prevMonthBillingPrice == 0) {
			return 100.0;
		}
		return ((double) (curMonthBillingPrice - prevMonthBillingPrice) / prevMonthBillingPrice) * 100;
	}

	public TopInfoRes getTopInfo(Long vendorId) {
		return new TopInfoRes(
			getTopFiveMember(vendorId),
			getRecentFiveContracts(vendorId)
		);
	}

	private List<TopFiveMemberRes> getTopFiveMember(Long vendorId) {
		List<TopFiveMemberQueryRes> queryRes = statRepository.findTopFiveMembers(vendorId);
		return queryRes.stream()
			.map(res -> new TopFiveMemberRes(res.getMemberName(), res.getTotalContractPrice(), res.getContractCount()))
			.toList();
	}

	private List<RecentFiveContractRes> getRecentFiveContracts(Long vendorId) {
		List<RecentFiveContractQueryRes> queryRes = statRepository.findRecentFiveContracts(vendorId);
		return queryRes.stream()
			.map(res -> new RecentFiveContractRes(
				res.getContractStartDate(),
				res.getMemberName(), res.getTotalContractPrice(),
				Period.between(res.getContractStartDate(), res.getContractEndDate()).getMonths()))
			.toList();
	}

	public MonthBillingInfoRes getMonthBillingInfo(Long vendorId, int year, int month) {
		List<MonthBillingQueryRes> monthBillingQueryRes = statRepository.findBillingsByMonth(vendorId, year, month);

		System.out.println("month: " + monthBillingQueryRes);

		// 상태별 그룹핑
		Map<BillingStatus, List<MonthBillingQueryRes>> statusToRes = monthBillingQueryRes.stream()
			.collect(Collectors.groupingBy(MonthBillingQueryRes::getBillingStatus));
		Arrays.stream(BillingStatus.values()).forEach(bs ->
			statusToRes.computeIfAbsent(bs, k -> new ArrayList<>())
		);

		// 일별 그룹핑
		List<DayBillingRes> dayBillingResList = monthBillingQueryRes.stream()
			.collect(Collectors.groupingBy(MonthBillingQueryRes::getBillingDate))
			.entrySet()
			.stream()
			.map(entry -> {
				LocalDate billingDate = entry.getKey();
				List<MonthBillingQueryRes> dayResList = entry.getValue();

				return new DayBillingRes(
					billingDate,
					calcBillingPrice(dayResList),
					dayResList.size()
				);
			})
			.toList();

		return new MonthBillingInfoRes(
				calcBillingPrice(monthBillingQueryRes),
				calcBillingPrice(statusToRes.get(BillingStatus.CREATED)),
				calcBillingPrice(statusToRes.get(BillingStatus.PAID)),
				calcBillingPrice(statusToRes.get(BillingStatus.WAITING_PAYMENT)),
				calcBillingPrice(statusToRes.get(BillingStatus.NON_PAID)),
				monthBillingQueryRes.size(),
				statusToRes.get(BillingStatus.CREATED).size(),
				statusToRes.get(BillingStatus.PAID).size(),
				statusToRes.get(BillingStatus.WAITING_PAYMENT).size(),
				statusToRes.get(BillingStatus.NON_PAID).size(),
				dayBillingResList);
	}

	// public DayBillingDetailRes getDayBillingDetail(Long vendorId, LocalDate date) {
	// 	List<Billing> billings = statRepository.findBillingsByDay(vendorId, date);
	//
	// 	// 상태별 그룹핑
	// 	Map<BillingStatus, List<Billing>> statusToRes = billings.stream()
	// 		.collect(Collectors.groupingBy(Billing::getBillingStatus));
	// 	Arrays.stream(BillingStatus.values()).forEach(bs ->
	// 		statusToRes.computeIfAbsent(bs, k -> new ArrayList<>())
	// 	);
	//
	// }

	private Long calcBillingPrice(List<MonthBillingQueryRes> monthBillingQueryRes) {
		return monthBillingQueryRes.stream()
			.mapToLong(MonthBillingQueryRes::getBillingPrice)
			.sum();
	}
}
