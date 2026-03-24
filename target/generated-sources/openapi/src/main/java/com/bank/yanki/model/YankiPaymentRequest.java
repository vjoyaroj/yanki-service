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
 * YankiPaymentRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public class YankiPaymentRequest {

  private String senderPhoneNumber;

  private String receiverPhoneNumber;

  private Double amount;

  private String description;

  private String idempotencyKey;

  public YankiPaymentRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public YankiPaymentRequest(String senderPhoneNumber, String receiverPhoneNumber, Double amount) {
    this.senderPhoneNumber = senderPhoneNumber;
    this.receiverPhoneNumber = receiverPhoneNumber;
    this.amount = amount;
  }

  public YankiPaymentRequest senderPhoneNumber(String senderPhoneNumber) {
    this.senderPhoneNumber = senderPhoneNumber;
    return this;
  }

  /**
   * Get senderPhoneNumber
   * @return senderPhoneNumber
  */
  @NotNull 
  @Schema(name = "senderPhoneNumber", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("senderPhoneNumber")
  public String getSenderPhoneNumber() {
    return senderPhoneNumber;
  }

  public void setSenderPhoneNumber(String senderPhoneNumber) {
    this.senderPhoneNumber = senderPhoneNumber;
  }

  public YankiPaymentRequest receiverPhoneNumber(String receiverPhoneNumber) {
    this.receiverPhoneNumber = receiverPhoneNumber;
    return this;
  }

  /**
   * Get receiverPhoneNumber
   * @return receiverPhoneNumber
  */
  @NotNull 
  @Schema(name = "receiverPhoneNumber", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("receiverPhoneNumber")
  public String getReceiverPhoneNumber() {
    return receiverPhoneNumber;
  }

  public void setReceiverPhoneNumber(String receiverPhoneNumber) {
    this.receiverPhoneNumber = receiverPhoneNumber;
  }

  public YankiPaymentRequest amount(Double amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
  */
  @NotNull 
  @Schema(name = "amount", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("amount")
  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public YankiPaymentRequest description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  
  @Schema(name = "description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public YankiPaymentRequest idempotencyKey(String idempotencyKey) {
    this.idempotencyKey = idempotencyKey;
    return this;
  }

  /**
   * Clave para evitar duplicados (opcional)
   * @return idempotencyKey
  */
  
  @Schema(name = "idempotencyKey", description = "Clave para evitar duplicados (opcional)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("idempotencyKey")
  public String getIdempotencyKey() {
    return idempotencyKey;
  }

  public void setIdempotencyKey(String idempotencyKey) {
    this.idempotencyKey = idempotencyKey;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    YankiPaymentRequest yankiPaymentRequest = (YankiPaymentRequest) o;
    return Objects.equals(this.senderPhoneNumber, yankiPaymentRequest.senderPhoneNumber) &&
        Objects.equals(this.receiverPhoneNumber, yankiPaymentRequest.receiverPhoneNumber) &&
        Objects.equals(this.amount, yankiPaymentRequest.amount) &&
        Objects.equals(this.description, yankiPaymentRequest.description) &&
        Objects.equals(this.idempotencyKey, yankiPaymentRequest.idempotencyKey);
  }

  @Override
  public int hashCode() {
    return Objects.hash(senderPhoneNumber, receiverPhoneNumber, amount, description, idempotencyKey);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class YankiPaymentRequest {\n");
    sb.append("    senderPhoneNumber: ").append(toIndentedString(senderPhoneNumber)).append("\n");
    sb.append("    receiverPhoneNumber: ").append(toIndentedString(receiverPhoneNumber)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    idempotencyKey: ").append(toIndentedString(idempotencyKey)).append("\n");
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

