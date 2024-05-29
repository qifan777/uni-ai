package io.qifan.infrastructure.generator.processor.model.common;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.StringUtils;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class Type extends ModelElement {
    protected String typeName;
    protected String packagePath;

    public String getTypePath() {
        return packagePath + "." + typeName;
    }

    public String getPackageDir() {
        return packagePath.replace(".", "/");
    }

    public String getFileName() {
        return typeName + ".java";
    }

    public String toFrontNameCase() {
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1-$2";
        return typeName.replaceAll(regex, replacement)
                .toLowerCase();
    }

    public String getUncapitalizeTypeName() {
        return StringUtils.uncapitalize(typeName);
    }

    @Override
    public Set<Type> getImportTypes() {
        return Set.of(this);
    }
}
