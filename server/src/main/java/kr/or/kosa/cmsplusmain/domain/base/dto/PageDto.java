package kr.or.kosa.cmsplusmain.domain.base.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class PageDto {

	@Getter
	@Setter
	@ToString
	public static class Req {
		private int page = 0;
		private int size = 10;
		private String sort;
		private String orderBy;

		public boolean isAsc() {
			if (sort == null) return false;
			return sort.equalsIgnoreCase("asc");
		}
	}
}
