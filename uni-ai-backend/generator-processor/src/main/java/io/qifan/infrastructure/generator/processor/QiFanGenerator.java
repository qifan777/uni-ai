package io.qifan.infrastructure.generator.processor;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.processor.processor.DefaultModelElementProcessorContext;
import io.qifan.infrastructure.generator.processor.processor.EntityCreateProcessor;
import io.qifan.infrastructure.generator.processor.processor.ModelElementProcessor;
import io.qifan.infrastructure.generator.processor.processor.RouterCreateProcessor;
import org.reflections.Reflections;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static org.reflections.scanners.Scanners.TypesAnnotated;

public class QiFanGenerator {

    Set<ModelElementProcessor<?, ?>> processors;

    public QiFanGenerator() {
        processors = new TreeSet<>((o1, o2) -> o2.getPriority() - o1.getPriority());
        processors.add(new EntityCreateProcessor());
    }

    public void process(String packageName, String outputPath) {
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> annotatedEntities =
                reflections.get(TypesAnnotated.with(GenEntity.class).asClass())
                        .stream().filter(Class::isInterface).collect(Collectors.toSet());
        new RouterCreateProcessor().porocess(annotatedEntities, outputPath);
        processEntityElements(annotatedEntities, outputPath);
    }


    public void processEntityElements(Set<Class<?>> typeElements, String outputPath) {
        for (Class<?> typeElement : typeElements) {
            DefaultModelElementProcessorContext defaultModelElementProcessorContext =
                    new DefaultModelElementProcessorContext(
                            typeElement, outputPath);
            processEntityTypeElement(defaultModelElementProcessorContext);
        }
    }

    private void processEntityTypeElement(ModelElementProcessor.ProcessorContext context) {
        Object model = null;
        for (ModelElementProcessor<?, ?> processor : processors) {
            model = process(context, processor, model);
        }
    }

    private <P, R> R process(ModelElementProcessor.ProcessorContext context,
                             ModelElementProcessor<P, R> processor,
                             Object modelElement) {
        @SuppressWarnings("unchecked")
        P sourceElement = (P) modelElement;
        return processor.process(context, sourceElement);
    }

}
