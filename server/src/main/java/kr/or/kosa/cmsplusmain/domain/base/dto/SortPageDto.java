package kr.or.kosa.cmsplusmain.domain.base.dto;

import lombok.*;

import java.util.List;

public class SortPageDto {

	@Getter
	@Setter
	@ToString
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
			return (page - 1) * size;
		}
	}

	@AllArgsConstructor
	@Getter
	public static class Res<T> {
		private int totalPage;
		private List<T> data;
	}
}
