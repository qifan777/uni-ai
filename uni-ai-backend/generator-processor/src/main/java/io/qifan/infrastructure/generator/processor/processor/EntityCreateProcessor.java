package io.qifan.infrastructure.generator.processor.processor;


import io.qifan.infrastructure.generator.processor.model.Entity;
import io.qifan.infrastructure.generator.processor.model.controller.ControllerForAdmin;
import io.qifan.infrastructure.generator.processor.model.controller.ControllerForFront;
import io.qifan.infrastructure.generator.processor.model.dto.Dto;
import io.qifan.infrastructure.generator.processor.model.front.*;
import io.qifan.infrastructure.generator.processor.model.repository.Repository;
import io.qifan.infrastructure.generator.processor.model.service.Service;
import io.qifan.infrastructure.generator.processor.utils.FieldUtils;
import io.qifan.infrastructure.generator.processor.utils.TypeUtils;
import io.qifan.infrastructure.generator.processor.writer.ModelWriter;

public class EntityCreateProcessor implements ModelElementProcessor<Void, Entity> {


    private final String sourcePath = "src/main/java";
    private Class<?> typeElement;

    @Override
    public Entity process(ProcessorContext processorContext, Void sourceModel) {
        typeElement = processorContext.typeElement();
        ModelWriter modelWriter = new ModelWriter(processorContext.outputPath());
        Entity entity = Entity.builder()
                .type(TypeUtils.getType(typeElement))
                .dto(createDto())
                .repository(createRepository())
                .service(createService())
                .controllerForAdmin(createControllerForAdmin())
                .controllerForFront(createControllerForUser())
                .query(query())
                .store(store())
                .table(table())
                .details(details())
                .view(view())
                .build();
        modelWriter.writeModel(entity.getStore(), false);
        modelWriter.writeModel(entity.getQuery(), false);
        modelWriter.writeModel(entity.getView(), false);
        modelWriter.writeModel(entity.getTable(), false);
        modelWriter.writeModel(entity.getDetails(), false);
        ControllerForAdmin controllerForAdmin = entity.getControllerForAdmin();
        ControllerForFront controllerForFront = entity.getControllerForFront();
        Service service = entity.getService();
        Repository repository = entity.getRepository();
        modelWriter.writeModel(controllerForAdmin.getSourcePath(), controllerForAdmin.getType(), controllerForAdmin);
        modelWriter.writeModel(controllerForFront.getSourcePath(), controllerForFront.getType(), controllerForFront);
        modelWriter.writeModel(service.getSourcePath(), service.getType(), service);
        modelWriter.writeModel(repository.getSourcePath(), repository.getType(), repository);
        modelWriter.writeModel(entity.getDto(), false);

        return entity;
    }

    private ControllerForAdmin createControllerForAdmin() {
        return ControllerForAdmin.builder()
                .sourcePath(sourcePath)
                .type(TypeUtils.getType(typeElement, "controller", "ForAdminController"))
                .entityType(TypeUtils.getType(typeElement))
                .build();
    }

    private ControllerForFront createControllerForUser() {
        return ControllerForFront.builder()
                .sourcePath(sourcePath)
                .type(TypeUtils.getType(typeElement, "controller", "ForFrontController"))
                .entityType(TypeUtils.getType(typeElement))
                .build();
    }

    private Service createService() {

        return Service.builder()
                .sourcePath(sourcePath)
                .type(TypeUtils.getType(typeElement, "service", "Service"))
                .entityType(TypeUtils.getType(typeElement))
                .build();
    }


    private Repository createRepository() {

        return Repository.builder()
                .sourcePath(sourcePath)
                .type(TypeUtils.getType(typeElement, "repository", "Repository"))
                .entityType(TypeUtils.getType(typeElement))
                .fields(FieldUtils.getItemFields(typeElement).stream().map(itemField -> {
                    itemField.setProp(itemField.getProp());
                    return new QueryItem(itemField);
                }).toList())
                .build();
    }


    private Dto createDto() {
        return Dto.builder()
                .sourcePath(sourcePath)
                .type(TypeUtils.getType(typeElement,
                        "",
                        ""))
                .entityType(TypeUtils.getType(typeElement))
                .fields(FieldUtils.getItemFields(typeElement))
                .build();
    }


    public Query query() {

        return Query.builder().entityType(TypeUtils.getType(typeElement))
                .queryItems(FieldUtils.getItemFields(typeElement).stream().map(itemField -> {
                    itemField.setProp(itemField.getProp());
                    return new QueryItem(itemField);
                }).toList())
                .build();
    }

    public Store store() {

        return Store.builder().entityType(TypeUtils.getType(typeElement)).build();
    }

    public Table table() {

        return Table.builder()
                .entityType(TypeUtils.getType(typeElement))
                .tableItems(FieldUtils.getItemFields(typeElement).stream().map(itemField -> {
                    itemField.setProp(itemField.getProp());
                    return new TableItem(itemField);
                }).toList())
                .build();
    }

    public Details details() {

        return Details.builder()
                .entityType(TypeUtils.getType(typeElement))
                .formItems(FieldUtils.getItemFields(typeElement).stream().map(itemField -> {
                    itemField.setProp(itemField.getProp());
                    return new FormItem(itemField);
                }).toList())
                .build();
    }


    public View view() {

        return View.builder().entityType(TypeUtils.getType(typeElement)).build();
    }


    @Override
    public int getPriority() {
        return 1;
    }
}
