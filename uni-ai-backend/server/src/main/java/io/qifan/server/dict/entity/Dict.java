package io.qifan.server.dict.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.server.infrastructure.jimmer.BaseEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.Key;

@Entity
@GenEntity
public interface Dict extends BaseEntity {

    @GenField(value = "值编号", order = -1)
    @Key
    int keyId();

    @GenField(value = "值名称", order = 0)
    String keyName();

    @GenField(value = "值英文名称", order = 1)
    String keyEnName();

    @GenField(value = "字典编号", order = 2)
    @Key
    int dictId();

    @GenField(value = "字典名称", order = 3)
    String dictName();

    @GenField(value = "字段英文名称", order = 4)
    String dictEnName();

    @GenField(value = "排序号", order = 5)
    int orderNum();

    @GenField(value = "是否可见", order = 6)
    boolean visible();
}
