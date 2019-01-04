package custom.userportal.domain;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author iveshtard
 * @since 11/16/2018
 */

@Document(collection = "bets")
public class Bet {

  @Id
  @JsonView(Views.Id.class)
  private String id;

  @JsonView(Views.IdViewName.class)
  private String text;

  @JsonView(Views.IdView.class)
  private String viewId;
  private String authorName;
  private String auctionId;

  public Bet() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getViewId() {
    return viewId;
  }

  public void setViewId(String viewId) {
    this.viewId = viewId;
  }

  public String getAuthorName() {
    return authorName;
  }

  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }

  public String getAuctionId() {
    return auctionId;
  }

  public void setAuctionId(String auctionId) {
    this.auctionId = auctionId;
  }

  @Override
  public String toString() {
    return "Bet{" +
      "id='" + id + '\'' +
      ", text='" + text + '\'' +
      ", viewId='" + viewId + '\'' +
      ", authorName='" + authorName + '\'' +
      ", auctionId='" + auctionId + '\'' +
      '}';
  }
}
