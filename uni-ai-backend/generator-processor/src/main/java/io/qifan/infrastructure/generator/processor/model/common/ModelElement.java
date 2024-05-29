package io.qifan.infrastructure.generator.processor.model.common;


import io.qifan.infrastructure.generator.processor.writer.FreeMarkerWritable;

import java.util.Set;

public abstract class ModelElement extends FreeMarkerWritable {
    public abstract Set<Type> getImportTypes();
}
