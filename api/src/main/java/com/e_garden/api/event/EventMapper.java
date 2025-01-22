package com.e_garden.api.event;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(source = "user.id", target = "userId")
    EventDTO toDto(Event event);

    @Mapping(source = "userId", target = "user.id")
    Event toEntity(EventDTO eventDTO);
}
