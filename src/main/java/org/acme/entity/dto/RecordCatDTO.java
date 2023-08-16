package org.acme.entity.dto;

import io.quarkus.hibernate.reactive.panache.common.ProjectedFieldName;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record RecordCatDTO (String name,@ProjectedFieldName("person.name") String masterName){}
