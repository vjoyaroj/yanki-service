package com.bank.yanki.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * LinkDebitCardRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public class LinkDebitCardRequest {

  private String debitCardId;

  public LinkDebitCardRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public LinkDebitCardRequest(String debitCardId) {
    this.debitCardId = debitCardId;
  }

  public LinkDebitCardRequest debitCardId(String debitCardId) {
    this.debitCardId = debitCardId;
    return this;
  }

  /**
   * Get debitCardId
   * @return debitCardId
  */
  @NotNull 
  @Schema(name = "debitCardId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("debitCardId")
  public String getDebitCardId() {
    return debitCardId;
  }

  public void setDebitCardId(String debitCardId) {
    this.debitCardId = debitCardId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LinkDebitCardRequest linkDebitCardRequest = (LinkDebitCardRequest) o;
    return Objects.equals(this.debitCardId, linkDebitCardRequest.debitCardId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(debitCardId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LinkDebitCardRequest {\n");
    sb.append("    debitCardId: ").append(toIndentedString(debitCardId)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

