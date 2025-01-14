package com.e_garden.api.Events;

import lombok.Getter;

public class EventDTO {
     Long id;
     String title;
     String description;
     Integer eventType;
     @Getter
     Long userId;

     public EventDTO() {
     }

     public EventDTO(Long id, String title, String description, Integer eventType, Long userId) {
          this.id = id;
          this.title = title;
          this.description = description;
          this.eventType = eventType;
          this.userId =userId;
     }
}
