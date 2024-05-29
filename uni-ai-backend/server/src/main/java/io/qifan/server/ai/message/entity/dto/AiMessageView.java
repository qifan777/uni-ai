package io.qifan.server.ai.message.entity.dto;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.server.ai.message.entity.AiMessage;
import io.qifan.server.ai.message.entity.AiMessageDraft;
import io.qifan.server.ai.message.entity.AiMessageFetcher;
import io.qifan.server.dict.model.DictConstants;
import org.babyfish.jimmer.View;
import org.babyfish.jimmer.internal.GeneratedBy;
import org.babyfish.jimmer.sql.fetcher.ViewMetadata;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@GeneratedBy(
        file = "<server>/src/main/dto/ai/AiMessage.dto"
)
@GenEntity
public class AiMessageView implements View<AiMessage> {
    public static final ViewMetadata<AiMessage, AiMessageView> METADATA =
            new ViewMetadata<AiMessage, AiMessageView>(
                    AiMessageFetcher.$
                            .content()
                            .type(),
                    AiMessageView::new
            );

    private List<Map<String, Object>> content;

    private DictConstants.AiMessageType type;

    public AiMessageView() {
    }

    public AiMessageView(@NotNull AiMessage base) {
        this.content = base.content();
        this.type = base.type();
    }

    /**
     * 消息内容
     */
    @NotNull
    @GenField(
            value = "消息内容",
            type = ItemType.INPUT_TEXT_AREA,
            order = 1
    )
    public List<Map<String, Object>> getContent() {
        if (content == null) {
            throw new IllegalStateException("The property \"content\" is not specified");
        }
        return content;
    }

    public void setContent(@NotNull List<Map<String, Object>> content) {
        this.content = content;
    }

    /**
     * 消息类型(用户/助手/系统)
     */
    @NotNull
    @GenField(
            value = "消息类型",
            type = ItemType.SELECTABLE,
            dictEnName = "AI_MESSAGE_TYPE",
            order = 0
    )
    public DictConstants.AiMessageType getType() {
        if (type == null) {
            throw new IllegalStateException("The property \"type\" is not specified");
        }
        return type;
    }

    public void setType(@NotNull DictConstants.AiMessageType type) {
        this.type = type;
    }

    @Override
    public AiMessage toEntity() {
        return AiMessageDraft.$.produce(__draft -> {
            __draft.setContent(content);
            __draft.setType(type);
        });
    }

    @Override
    public int hashCode() {
        int hash = Objects.hashCode(content);
        hash = hash * 31 + Objects.hashCode(type);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        AiMessageView other = (AiMessageView) o;
        if (!Objects.equals(content, other.content)) {
            return false;
        }
        if (!Objects.equals(type, other.type)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AiMessageView").append('(');
        builder.append("content=").append(content);
        builder.append(", type=").append(type);
        builder.append(')');
        return builder.toString();
    }
}
