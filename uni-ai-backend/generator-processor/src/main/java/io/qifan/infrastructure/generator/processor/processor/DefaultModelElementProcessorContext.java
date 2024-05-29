package io.qifan.infrastructure.generator.processor.processor;

public record DefaultModelElementProcessorContext(Class<?> typeElement, String outputPath)
        implements ModelElementProcessor.ProcessorContext {
}
