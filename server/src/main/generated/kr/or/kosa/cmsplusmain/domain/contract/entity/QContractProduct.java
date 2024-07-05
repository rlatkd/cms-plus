package kr.or.kosa.cmsplusmain.domain.contract.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QContractProduct is a Querydsl query type for ContractProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QContractProduct extends EntityPathBase<ContractProduct> {

    private static final long serialVersionUID = 991749294L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QContractProduct contractProduct = new QContractProduct("contractProduct");

    public final kr.or.kosa.cmsplusmain.domain.base.entity.QBaseEntity _super = new kr.or.kosa.cmsplusmain.domain.base.entity.QBaseEntity(this);

    public final QContract contract;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDateTime = _super.createdDateTime;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedDateTime = _super.deletedDateTime;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final kr.or.kosa.cmsplusmain.domain.product.entity.QProduct product;

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDateTime = _super.updatedDateTime;

    public QContractProduct(String variable) {
        this(ContractProduct.class, forVariable(variable), INITS);
    }

    public QContractProduct(Path<? extends ContractProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QContractProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QContractProduct(PathMetadata metadata, PathInits inits) {
        this(ContractProduct.class, metadata, inits);
    }

    public QContractProduct(Class<? extends ContractProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.contract = inits.isInitialized("contract") ? new QContract(forProperty("contract"), inits.get("contract")) : null;
        this.product = inits.isInitialized("product") ? new kr.or.kosa.cmsplusmain.domain.product.entity.QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

