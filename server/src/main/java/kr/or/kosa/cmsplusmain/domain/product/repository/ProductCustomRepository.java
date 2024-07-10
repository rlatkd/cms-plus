package kr.or.kosa.cmsplusmain.domain.product.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kr.or.kosa.cmsplusmain.domain.base.dto.SortPageDto;
import kr.or.kosa.cmsplusmain.domain.base.repository.BaseCustomRepository;
import kr.or.kosa.cmsplusmain.domain.product.dto.ProductRes;
import kr.or.kosa.cmsplusmain.domain.product.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static kr.or.kosa.cmsplusmain.domain.contract.entity.QContract.contract;
import static kr.or.kosa.cmsplusmain.domain.contract.entity.QContractProduct.contractProduct;
import static kr.or.kosa.cmsplusmain.domain.product.entity.QProduct.product;
import static kr.or.kosa.cmsplusmain.domain.vendor.entity.QVendor.vendor;

@Repository
public class ProductCustomRepository extends BaseCustomRepository<Product> {

    public ProductCustomRepository(EntityManager em, JPAQueryFactory jpaQueryFactory) {
        super(em, jpaQueryFactory);
    }

    // 상품이 매핑되어있는 계약수
    public int getContractNumber(Long productId) {
        /*
         * 상품이 포함된 계약의 개수를 구함
         * select count(distinct pc.contract_id)
         * from contract_product cp
         * join contract c
         * where cp.product_id = #{productId} and c.deleted = 0
         * */
        return jpaQueryFactory
                .select(contract.id.countDistinct())
                .from(contractProduct)
                .join(contractProduct.contract, contract) // 계약-상품 테이블의 외래키인 계약ID를 타고 들어감
                .where(contractProduct.product.id.eq(productId),
                        contractNotDel())
                .fetchOne().intValue();
    }

    // 고객의 상품 목록
    public List<Tuple> findProductListWithCondition(String vendorUserName, SortPageDto.Req pageable) {
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
         * select vendor_product.* count(distinct pc.contract_id)
         * from (select *
         *         from product p
         *         join vendor v
         *         where v.vendor_userName = #{userName} and p.deleted = 0) vp , product_contract pc
         * join contract c
         * where pc.product_id = vp.product_id and c.deleted = 0
         *
         * 2. (최종)
         * select p.*, count(distinct cp.contract_id) as contract_count
         * from product p
         * join vendor v on v.vendor_id = p.vendor_id
         * left join contract_product cp on cp.product_id = p.product_id and cp.deleted = 0
         * left join contract c on c.contract_id = cp.contract_id and c.deleted = 0
         * where v.vendor_username = 'vendor1' and p.deleted = 0
         * group by p.product_id;
         * */
        List<Tuple> result = jpaQueryFactory // 프로젝션 대상이 2개이므로 튜플 사용
                .select(product, contractProduct.contract.countDistinct())
                .from(product)
                .join(product.vendor, vendor)
                .leftJoin(contractProduct)
                .on(contractProduct.product.eq(product),
                        (contractProductNotDel()))
                .leftJoin(contract)
                .on(contractProduct.contract.eq(contract),
                        (contractNotDel()))
                .where(
                        vendor.username.eq(vendorUserName),
                        productNotDel()
                )
                .groupBy(product.id)
                .offset(pageable.getPage())
                .limit(pageable.getSize())
                .fetch();

        return result;
    }

    // 고객의 상품 총 갯수
    public int countAllProducts(String vendorUsername) {
        return jpaQueryFactory
                .select(product.id.count())
                .from(product)
                .join(product.vendor, vendor)
                .where(
                        vendorUsernameEq(vendorUsername),
                        productNotDel()
                )
                .fetchOne().intValue();
    }

    // 특정 고객이 본인의 상품이 아닌거 조회하는거 처리
    public boolean isExistProductByUsername(Long productId, String vendorUsername) {
        /*
         * 고객테이블에 있는 고객이름이 해당 상품의 고객ID의 이름인지 확인
         * select 1
         * from product p
         * join vendor v on p.vendor_id = v.vendor_id
         * where p.product_id = #{productId} and v.vendor_username = #{vendorUsername}
         * */
        Integer count = jpaQueryFactory
                .selectOne()
                .from(product)
                .join(product.vendor, vendor)
                .where(product.id.eq(productId),
                        vendor.username.eq(vendorUsername))
                .fetchFirst();

        return count != null;
    }

}