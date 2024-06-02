package io.qifan.server.ai.document.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.server.ai.collection.entity.AiCollection;
import io.qifan.server.infrastructure.jimmer.BaseEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.ManyToOne;


/**
 * <p>
 * 知识库文档
 * </p>
 */
@GenEntity
@Entity
public interface AiDocument extends BaseEntity {
    @GenField(value = "名称")
    String name();

    /**
     * 文档链接
     */
    @GenField(value = "文档链接", order = 0)
    String url();

    @GenField(value = "知识库", order = 1, type = ItemType.ASSOCIATION_SELECT)
    @IdView
    String aiCollectionId();

    @ManyToOne
    AiCollection aiCollection();
}
