package io.qifan.server.ai.collection.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.server.ai.model.entity.AiModel;
import io.qifan.server.infrastructure.jimmer.BaseEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.ManyToOne;


/**
 * <p>
 * 知识库
 *
 * </p>
 *
 * @author a1507
 * @date 2024-05-30
 */
@Entity
@GenEntity
public interface AiCollection extends BaseEntity {

    /**
     * 知识库名称
     */
    @GenField(value = "中文名称", order = 0)
    String name();

    @GenField(value = "英文名称", order = 1)
    String collectionName();

    /**
     * 嵌入模型
     */
    @GenField(value = "嵌入模型", order = 1, type = ItemType.ASSOCIATION_SELECT)
    @IdView
    String embeddingModelId();

    @ManyToOne
    AiModel embeddingModel();

}
