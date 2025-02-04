package com.e_garden.api.event;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


/**
 * L'interface Event mapper.
 */
@Mapper(componentModel = "spring")
public interface EventMapper {

    /**
     * To dto event dto.
     *
     * @param event le event
     * @return le event dto
     */
    @Mapping(source = "user.id", target = "userId")
    EventDTO toDto(Event event);

    /**
     * To entity event.
     *
     * @param eventDTO le event dto
     * @return le event
     */
    @Mapping(source = "userId", target = "user.id")
    Event toEntity(EventDTO eventDTO);
}
