package yapp.be.storage.jpa.volunteer.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserEntity is a Querydsl query type for UserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserEntity extends EntityPathBase<VolunteerEntity> {

    private static final long serialVersionUID = 52575243L;

    public static final QUserEntity userEntity = new QUserEntity("userEntity");

    public final yapp.be.storage.jpa.common.model.QBaseTimeEntity _super = new yapp.be.storage.jpa.common.model.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final BooleanPath deleted = createBoolean("deleted");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath nickname = createString("nickname");

    public final StringPath oAuthAccessToken = createString("oAuthAccessToken");

    public final StringPath oAuthRefreshToken = createString("oAuthRefreshToken");

    public final StringPath phone = createString("phone");

    public QUserEntity(String variable) {
        super(VolunteerEntity.class, forVariable(variable));
    }

    public QUserEntity(Path<VolunteerEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserEntity(PathMetadata metadata) {
        super(VolunteerEntity.class, metadata);
    }

}

