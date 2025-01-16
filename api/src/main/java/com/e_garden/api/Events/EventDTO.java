package com.e_garden.api.Events;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventDTO {
     private Long id;
     private String title;
     private String description;
     private Integer eventType;
     private Long userId;

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
