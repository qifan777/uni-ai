package io.qifan.infrastructure.generator.processor.model;


import io.qifan.infrastructure.generator.processor.model.common.ModelElement;
import io.qifan.infrastructure.generator.processor.model.common.Type;
import io.qifan.infrastructure.generator.processor.model.controller.ControllerForAdmin;
import io.qifan.infrastructure.generator.processor.model.controller.ControllerForFront;
import io.qifan.infrastructure.generator.processor.model.dto.Dto;
import io.qifan.infrastructure.generator.processor.model.front.*;
import io.qifan.infrastructure.generator.processor.model.repository.Repository;
import io.qifan.infrastructure.generator.processor.model.service.Service;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class Entity extends ModelElement {

    private Type type;
    private ControllerForAdmin controllerForAdmin;
    private ControllerForFront controllerForFront;
    private Dto dto;
    private Repository repository;
    private Service service;
    private View view;
    private Query query;
    private Store store;
    private Table table;
    private Details details;

    @Override
    public Set<Type> getImportTypes() {
        return null;
    }
}
