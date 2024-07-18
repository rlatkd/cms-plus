package kr.or.kosa.cmsplusmain.domain.excel.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.or.kosa.cmsplusmain.domain.member.dto.MemberExcelDto;
import kr.or.kosa.cmsplusmain.domain.excel.service.ExcelHandler;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/excel")
@RequiredArgsConstructor
public class ExcelController {

	private final ExcelHandler<MemberExcelDto> excelHandler = new ExcelHandler<>();

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<List<MemberExcelDto>> getExcelResult(MultipartFile file) {
		//your code
		return ResponseEntity.ok(excelHandler.handleExcelUpload(file, MemberExcelDto.class));
	}
}
