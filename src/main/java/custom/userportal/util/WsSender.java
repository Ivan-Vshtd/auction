package custom.userportal.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import custom.userportal.dto.EventType;
import custom.userportal.dto.ObjectType;
import custom.userportal.dto.WsEventDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

/**
 * @author iveshtard
 * @since 1/3/2019
 * class has been added to send via webSocket just notifications (events), main controls - through REST api
 */

@Component
public class WsSender {

  private final SimpMessagingTemplate template;
  private final ObjectMapper mapper;

  public WsSender(SimpMessagingTemplate template, ObjectMapper mapper) {
    this.template = template;
    this.mapper = mapper;
  }

  public <T> BiConsumer<EventType, T> getSender(ObjectType objectType, Class view){
    ObjectWriter writer = mapper
                            .setConfig(mapper.getSerializationConfig())
                            .writerWithView(view);
    return (EventType eventType, T payload) -> {
      String value = null;
      try {
        value = writer.writeValueAsString(payload);
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
      template.convertAndSend(
        "/topic/activity",
        new WsEventDto(objectType, eventType, value));
    };
  }
}
