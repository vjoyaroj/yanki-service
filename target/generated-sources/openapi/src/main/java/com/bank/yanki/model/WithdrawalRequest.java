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
 * WithdrawalRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public class WithdrawalRequest {

  private String walletId;

  private Double amount;

  private String description;

  public WithdrawalRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public WithdrawalRequest(String walletId, Double amount) {
    this.walletId = walletId;
    this.amount = amount;
  }

  public WithdrawalRequest walletId(String walletId) {
    this.walletId = walletId;
    return this;
  }

  /**
   * Get walletId
   * @return walletId
  */
  @NotNull 
  @Schema(name = "walletId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("walletId")
  public String getWalletId() {
    return walletId;
  }

  public void setWalletId(String walletId) {
    this.walletId = walletId;
  }

  public WithdrawalRequest amount(Double amount) {
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

  public WithdrawalRequest description(String description) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WithdrawalRequest withdrawalRequest = (WithdrawalRequest) o;
    return Objects.equals(this.walletId, withdrawalRequest.walletId) &&
        Objects.equals(this.amount, withdrawalRequest.amount) &&
        Objects.equals(this.description, withdrawalRequest.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(walletId, amount, description);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WithdrawalRequest {\n");
    sb.append("    walletId: ").append(toIndentedString(walletId)).append("\n");
    sb.append("    amount: ").append(toIndentedString(amount)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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

