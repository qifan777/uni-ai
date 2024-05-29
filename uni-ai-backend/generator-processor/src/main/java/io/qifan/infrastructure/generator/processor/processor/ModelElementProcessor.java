package io.qifan.infrastructure.generator.processor.processor;


public interface ModelElementProcessor<P, R> {


    R process(ProcessorContext context, P sourceModel);

    int getPriority();

    interface ProcessorContext {

        Class<?> typeElement();

        String outputPath();
    }
}
