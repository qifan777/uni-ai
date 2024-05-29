package io.qifan.infrastructure.generator.processor.writer;

import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.Writer;

public abstract class FreeMarkerWritable implements Writable {

    @Override
    public void write(Context context, Writer writer) {
        try {
            new FreeMarkerModelElementWriter().write(this, context, writer);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    protected String getTemplateName() {
        return getTemplateNameForClass(getClass());
    }

    protected String getTemplateNameForClass(Class<?> clazz) {
        return clazz.getName().replace('.', '/') + ".ftl";
    }
}
