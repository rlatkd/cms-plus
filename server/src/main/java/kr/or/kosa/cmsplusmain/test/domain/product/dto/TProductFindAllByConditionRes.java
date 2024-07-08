package kr.or.kosa.cmsplusmain.test.domain.product.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TProductFindAllByConditionRes { // 상품 목록 조회(조건에 따른)

    private int page;
    private int offset;
    private int totalPage;
    private int totalCount;
    private List<TProductDto> data; // 페이지네이션 데이터

}
