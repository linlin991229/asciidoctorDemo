package org.acme.entity.dto;

import io.quarkus.hibernate.reactive.panache.common.ProjectedFieldName;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class CatDTO {
    private final String name;
    private final String masterName;

    public CatDTO(String name, @ProjectedFieldName("person.name") String masterName) {
        this.name = name;
        this.masterName = masterName;
    }

    public String getMasterName() {
        return masterName;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CatDTO{" +
               "name='" + name + '\'' +
               ", masterName='" + masterName + '\'' +
               '}';
    }
}
