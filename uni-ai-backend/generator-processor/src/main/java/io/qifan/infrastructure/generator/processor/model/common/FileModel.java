package io.qifan.infrastructure.generator.processor.model.common;


import io.qifan.infrastructure.generator.processor.writer.FreeMarkerWritable;

public abstract class FileModel extends FreeMarkerWritable {
    public abstract String getFileName();
}
