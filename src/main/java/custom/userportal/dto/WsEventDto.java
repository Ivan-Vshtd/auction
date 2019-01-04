package custom.userportal.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonView;
import custom.userportal.domain.Views;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author iveshtard
 * @since 1/3/2019
 */
@Data
@AllArgsConstructor
@JsonView(Views.IdView.class)
public class WsEventDto {
  private ObjectType objectType;
  private EventType eventType;

  @JsonRawValue
  private String body;
}
