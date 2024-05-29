package io.qifan.infrastructure.generator.processor.writer;

import freemarker.core.Environment;
import freemarker.ext.beans.BeanModel;
import freemarker.template.Configuration;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateModel;

import java.util.HashMap;
import java.util.Map;

public class ModelIncludeDirective implements TemplateDirectiveModel {
    private final Configuration configuration;

    public ModelIncludeDirective(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body) {

        Writable modelElement = getModelElement(params);
        ModelWriter.DefaultModelElementWriterContext context = createContext(params);
        if (modelElement != null) {
            modelElement.write(context, env.getOut());
        }
    }

    @SuppressWarnings("rawtypes")
    private Writable getModelElement(Map params) {
        if (!params.containsKey("object")) {
            throw new IllegalArgumentException(
                    "Object to be included must be passed to this directive via the 'object' parameter"
            );
        }

        BeanModel objectModel = (BeanModel) params.get("object");

        if (objectModel == null) {
            return null;
        }

        if (!(objectModel.getWrappedObject() instanceof Writable)) {
            throw new IllegalArgumentException("Given object isn't a Writable:" + objectModel.getWrappedObject());
        }

        return (Writable) objectModel.getWrappedObject();
    }

    /**
     * Creates a writer context providing access to the FreeMarker
     * {@link Configuration} and a map with any additional parameters passed to
     * the directive.
     *
     * @param params The parameter map passed to this directive.
     * @return A writer context.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private ModelWriter.DefaultModelElementWriterContext createContext(Map params) {
        Map<String, Object> ext = new HashMap<String, Object>(params);
        ext.remove("object");

        Map<Class<?>, Object> values = new HashMap<>();
        values.put(Configuration.class, configuration);
        values.put(Map.class, ext);

        return new ModelWriter.DefaultModelElementWriterContext(values);
    }
}
