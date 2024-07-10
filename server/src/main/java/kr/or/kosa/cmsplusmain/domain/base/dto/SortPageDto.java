package kr.or.kosa.cmsplusmain.domain.base.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class SortPageDto {

	@Getter
	@Setter
	public static class Req {

		private int page = 1;
		private int size = 10;

		private String sort;
		private String orderBy;

		public boolean isAsc() {
			if (sort == null)
				return false;
			return sort.equalsIgnoreCase("asc");
		}

		public int getPage() {
			return page - 1;
		}
	}
}
