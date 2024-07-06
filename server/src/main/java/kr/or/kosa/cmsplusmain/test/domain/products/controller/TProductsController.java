package kr.or.kosa.cmsplusmain.test.domain.products.controller;

import kr.or.kosa.cmsplusmain.test.domain.products.dto.TProductsResponseDto;
import kr.or.kosa.cmsplusmain.test.domain.products.entity.TProducts;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/vendor/products")
public class TProductsController {

    private List<TProducts> TProductsList = Arrays.asList(
            TProducts.builder().name("제목1").price(1000.0).contractCount(5).createdAt("2024-07-07").notes("내용1").build(),
            TProducts.builder().name("제목2").price(2000.0).contractCount(10).createdAt("2024-07-08").notes("내용2").build(),
            TProducts.builder().name("제목3").price(3000.0).contractCount(15).createdAt("2024-07-09").notes("내용3").build()
    );

    @GetMapping
    public ResponseEntity<Map<String, Object>> getProducts(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "2") int size) {
        int start = page * size;
        int end = Math.min((page + 1) * size, TProductsList.size());

        if (start > TProductsList.size()) {
            return ResponseEntity.ok(Map.of(
                    "page", page,
                    "size", size,
                    "totalPage", (TProductsList.size() + size - 1) / size,
                    "totalCount", TProductsList.size(),
                    "data", Collections.emptyList()
            ));
        }

        List<TProductsResponseDto> productsResponseList = this.TProductsList.subList(start, end).stream()
                .map(TProductsResponseDto::fromEntity)
                .collect(Collectors.toList());

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("page", page);
        response.put("size", size);
        response.put("totalPage", (this.TProductsList.size() + size - 1) / size);
        response.put("totalCount", this.TProductsList.size());
        response.put("data", productsResponseList);

        return ResponseEntity.ok(response);
    }
}