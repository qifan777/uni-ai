package io.qifan.infrastructure.generator.processor.writer;

import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.SimpleMapModel;
import freemarker.template.*;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class FreeMarkerModelElementWriter {
    public void write(FreeMarkerWritable writable, Writable.Context context, Writer writer) throws IOException,
            TemplateException {
        Configuration configuration = context.get(Configuration.class);
        Template template = configuration.getTemplate(writable.getTemplateName());
        template.process(new ExternalParamsTemplateModel(new BeanModel(writable, BeansWrapper.getDefaultInstance()),
                        new SimpleMapModel(context.get(
                                Map.class), BeansWrapper.getDefaultInstance())),
                writer);
    }

    private record ExternalParamsTemplateModel(BeanModel object, SimpleMapModel extParams) implements
            TemplateHashModel {

        @Override
        public TemplateModel get(String key) throws TemplateModelException {
            if (key.equals("ext")) {
                return extParams;
            } else {
                return object.get(key);
            }
        }

        @Override
        public boolean isEmpty() throws TemplateModelException {
            return object.isEmpty() && extParams.isEmpty();
        }
    }
}
