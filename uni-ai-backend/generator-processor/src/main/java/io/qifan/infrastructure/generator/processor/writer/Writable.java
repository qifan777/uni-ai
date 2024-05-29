package io.qifan.infrastructure.generator.processor.writer;

import java.io.Writer;

public interface Writable {
    void write(Context context, Writer writer);

    interface Context {
        <T> T get(Class<T> type);
    }
}
