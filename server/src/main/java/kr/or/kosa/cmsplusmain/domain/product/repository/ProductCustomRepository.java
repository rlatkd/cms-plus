package kr.or.kosa.cmsplusmain.domain.product.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kr.or.kosa.cmsplusmain.domain.base.dto.PageReq;
import kr.or.kosa.cmsplusmain.domain.base.repository.BaseCustomRepository;
import kr.or.kosa.cmsplusmain.domain.product.dto.ProductQueryDto;
import kr.or.kosa.cmsplusmain.domain.product.dto.ProductSearchReq;
import kr.or.kosa.cmsplusmain.domain.product.dto.QProductQueryDto;
import kr.or.kosa.cmsplusmain.domain.product.entity.Product;
import lombok.extern.slf4j.Slf4j;
import kr.or.kosa.cmsplusmain.domain.product.entity.ProductStatus;
import org.springframework.stereotype.Repository;
import java.util.List;

import static kr.or.kosa.cmsplusmain.domain.contract.entity.QContract.contract;
import static kr.or.kosa.cmsplusmain.domain.contract.entity.QContractProduct.contractProduct;
import static kr.or.kosa.cmsplusmain.domain.product.entity.QProduct.product;
import static kr.or.kosa.cmsplusmain.domain.vendor.entity.QVendor.vendor;

@Slf4j
@Repository
public class ProductCustomRepository extends BaseCustomRepository<Product> {

    public ProductCustomRepository(EntityManager em, JPAQueryFactory jpaQueryFactory) {
        super(em, jpaQueryFactory);
    }

    // 상품이 매핑되어있는 계약수
    public int getContractNumber(Long productId) {
        /*
         * 상품이 포함된 계약의 개수를 구함
         * select count(distinct cp.contract_id)
         * from contract_product cp
         * join contract c
         * where cp.product_id = #{productId} and c.deleted = 0
         * */
        Long res = jpaQueryFactory
                .select(contract.id.countDistinct())
                .from(contractProduct)
                .join(contractProduct.contract, contract) // 계약-상품 테이블의 외래키인 계약ID를 타고 들어감
                .where(contractProduct.product.id.eq(productId),
                        contractNotDel())
                .fetchOne();

        return (res == null) ? 0 : res.intValue();
    }

    // 고객의 상품 목록
    // 프로젝션 대상이 2개이상이기 때문에 QueryProjection을 이용
    public List<ProductQueryDto> findProductListWithCondition(Long vendorId, ProductSearchReq search, PageReq pageable) {
        /*
         * 해당 고객의 모든 상품(삭제되지않은)을 가져오는데, 상품의 정보와 상품이 포함된 계약 갯수를 가져와야함
         *
         * 해당 고객의 모든 상품(삭제되지않은)
         * select *
         * from product p
         * join vendor v
         * where v.vendor_userName = #{userName} and p.deleted = 0
         *
         * 상품의 정보와 상품이 포함된 계약 갯수
         * select count(distinct pc.contract_id)
         * from product_contract pc
         * join contract c
         * where pc.product_id = #{productId} and c.deleted = 0
         *
         * 1. 해당 고객의 모든 상품(삭제되지않은)을 가져오는데, 상품의 정보와 상품이 포함된 계약 갯수를 가져와야함
         * select vendor_product.* count(distinct cp.contract_id)
         * from (select *
         *         from product p
         *         join vendor v
         *         where v.vendor_userName = #{userName} and p.deleted = 0) vp , contract_product cp
         * join contract c
         * where cp.product_id = vp.product_id and c.deleted = 0
         *
         * 2. (최종)
         * select p.*, count(distinct cp.contract_id) as contract_count
         * from product p
         * join vendor v on v.vendor_id = p.vendor_id
         * left join contract_product cp on cp.product_id = p.product_id and cp.deleted = 0
         * left join contract c on c.contract_id = cp.contract_id and c.deleted = 0
         * where v.vendor_username = 'vendor1' and p.deleted = 0
         * group by p.product_id
         *
         * 3. (JOIN문 줄이는 개선)
         * select p.*, count(istinct cp.contract_id) as contract_count
         * from product p
         * left join contract_product cp on cp.product_id = p.product_id and cp.deleted = 0
         * left join contract c on c.contract_id = cp.contract_id and c.deleted = 0
         * where ~
         * group by p.product_id
         * */
        List<ProductQueryDto> res = jpaQueryFactory
                .select(new QProductQueryDto(product, contractProduct.contract.countDistinct().intValue())) //ProductQRes의 QueryDSL DTO 생성
                .from(product)
                // .join(product.vendor, vendor) // 조인문 제거
                .leftJoin(contractProduct)
                        .on(contractProduct.product.eq(product), (contractProductNotDel()))
                .leftJoin(contract)
                        .on(contractProduct.contract.eq(contract), (contractNotDel()))
                .where(
                        productNotDel(),                                        // 상품 소프트 삭제
                        productVendorIdEq(vendorId), // 상품의 고객 아이디 일치(수정된 로직); product.vendor.id.eq(vendorId)
                        productNameContains(search.getProductName()),           // 상품 이름 포함
                        productPriceLoe(search.getProductPrice()),              // 상품 가격 이하
                        productMemoContains(search.getProductMemo()),           // 상품 비고 포함
                        productCreatedDateEq(search.getProductCreatedDate())    // 상품 생성일 일치
                )
                .groupBy(product.id)
                .having( // 집계함수(ex. count)에 대한 조건문은 where절이 아닌 having절에서 사용함
                        contractNumberLoe(search.getContractNumber())           // 상품들의 각 해당하는 계약수 이하
                )
                .offset(pageable.getPage())
                .limit(pageable.getSize())
                .fetch();

        return res;
    }

    // (조건이 있다면 조건에 따른)고객의 상품 총 갯수
    public int countAllProductsWithCondition(Long vendorId, ProductSearchReq search) {
        Long res = jpaQueryFactory
                .select(product.id.count())
                .from(product)
                .where(
                        //상품 총 갯수에도 search를 반영해야함, 단 having절은 반영X
                        productNotDel(),
                        productVendorIdEq(vendorId),
                        productNameContains(search.getProductName()),
                        productPriceLoe(search.getProductPrice()),
                        productMemoContains(search.getProductMemo()),
                        productCreatedDateEq(search.getProductCreatedDate())
                )
                .fetchOne();

        return (res == null) ? 0 : res.intValue();
    }

    // 특정 고객이 본인의 상품이 아닌거 조회하는거 처리
    public boolean isExistProductByUsername(Long productId, Long vendorId) {
        /*
         * 고객테이블에 있는 고객이름이 해당 상품의 고객ID의 이름인지 확인
         * select 1
         * from product p
         * join vendor v on p.vendor_id = v.vendor_id
         * where p.product_id = #{productId} and v.vendor_username = #{vendorUsername}
         * */
        Integer res = jpaQueryFactory
                .selectOne()
                .from(product)
                .join(product.vendor, vendor)
                .where(product.id.eq(productId),
                        vendor.id.eq(vendorId))
                .fetchFirst();

        return res != null;
    }


    /* 고객의 상품 목록 조회 */
    public List<Product> findAvailableProductsByVendorUsername(String username) {
        return jpaQueryFactory
                .selectFrom(product)
                .join(product.vendor, vendor)
                .where(
                        vendorUsernameEq(username),
                        productNotDel()
                )
                .fetch();
    }

    /* 전체 상품 목록 조회 */
    public List<Product> findProducts() {
        return jpaQueryFactory
                .selectFrom(product)
                .where(productNotDel())
                .fetch();
    }


}

