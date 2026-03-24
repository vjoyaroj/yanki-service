package com.bank.yanki.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * YankiPaymentResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public class YankiPaymentResponse {

  private String paymentId;

  private String senderWalletId;

  private String receiverWalletId;

  private Double amount;

  private String description;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime createdAt;

  public YankiPaymentResponse paymentId(String paymentId) {
    this.paymentId = paymentId;
    return this;
  }

  /**
   * Get paymentId
   * @return paymentId
  */
  
  @Schema(name = "paymentId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("paymentId")
  public String getPaymentId() {
    return paymentId;
  }

  public void setPaymentId(String paymentId) {
    this.paymentId = paymentId;
  }

  public YankiPaymentResponse senderWalletId(String senderWalletId) {
    this.senderWalletId = senderWalletId;
    return this;
  }

  /**
   * Get senderWalletId
   * @return senderWalletId
  */
  
  @Schema(name = "senderWalletId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("senderWalletId")
  public String getSenderWalletId() {
    return senderWalletId;
  }

  public void setSenderWalletId(String senderWalletId) {
    this.senderWalletId = senderWalletId;
  }

  public YankiPaymentResponse receiverWalletId(String receiverWalletId) {
    this.receiverWalletId = receiverWalletId;
    return this;
  }

  /**
   * Get receiverWalletId
   * @return receiverWalletId
  */
  
  @Schema(name = "receiverWalletId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("receiverWalletId")
  public String getReceiverWalletId() {
    return receiverWalletId;
  }

  public void setReceiverWalletId(String receiverWalletId) {
    this.receiverWalletId = receiverWalletId;
  }

  public YankiPaymentResponse amount(Double amount) {
    this.amount = amount;
    return this;
  }

  /**
   * Get amount
   * @return amount
  */
  
  @Schema(name = "amount", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("amount")
  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public YankiPaymentResponse description(String description) {
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

  public YankiPaymentResponse createdAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Get createdAt
   * @return createdAt
  */
  @Valid 
  @Schema(name = "createdAt", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("createdAt")
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    YankiPaymentResponse yankiPaymentResponse = (YankiPaymentResponse) o;
    return Objects.equals(this.paymentId, yankiPaymentResponse.paymentId) &&
        Objects.equals(this.senderWalletId, yankiPaymentResponse.senderWalletId) &&
        Objects.equals(this.receiverWalletId, yankiPaymentResponse.receiverWalletId) &&
        Objects.equals(this.amount, yankiPaymentResponse.amount) &&
        Objects.equals(this.description, yankiPaymentResponse.description) &&
        Objects.equals(this.createdAt, yankiPaymentResponse.createdAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(paymentId, senderWalletId, receiverWalletId, amount, description, createdAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class YankiPaymentResponse {\n");
    sb.append("    paymentId: ").append(toIndentedString(paymentId)).append("\n");
    sb.append("    senderWalletId: ").append(toIndentedString(senderWalletId)).append("\n");
    sb.append("    receiverWalletId: ").append(toIndentedString(receiverWalletId)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
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

