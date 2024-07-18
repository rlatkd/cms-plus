package kr.or.kosa.cmsplusmain.domain.excel.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExcelErrorRes<T> {
	private T notSaved;
	private String message;
}
