package kr.or.kosa.cmsplusmain.domain.base.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageReq {
	private int page = 1;
	private int size = 9; // 화면상 사이즈 9가 나음

	private Order order;
	private String orderBy;

	/*
	* 오름차순 여부
	* */
	public boolean isAsc() {
		return order == Order.ASC;
	}

	/*
	* 페이지 번호
	* 프론트에서 페이지 번호는 1부터 시작하므로 -1
	* */
	public int getPage() {
		return (page < 1) ? 0 : (page - 1) * size;
	}

	/*
	* 오름차순, 내림차순
	* */
	public enum Order {
		ASC, DESC
	}
}
