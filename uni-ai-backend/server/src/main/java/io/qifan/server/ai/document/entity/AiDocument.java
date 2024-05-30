package io.qifan.server.ai.document.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.server.ai.model.entity.AiModel;
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
    /**
     * 文档链接
     */
    @GenField(value = "文档链接")
    String url();

    /**
     * 集合名称(表名)
     */
    @GenField(value = "集合名称(表名)")
    String collection();

    /**
     * 总结
     */
    @GenField(value = "总结")
    String summary();

    @IdView
    String embeddingModelId();

    @IdView
    String summaryModelId();

    @ManyToOne
    AiModel embeddingModel();

    @ManyToOne
    AiModel summaryModel();
}
