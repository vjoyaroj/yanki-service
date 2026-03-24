package com.bank.yanki.model;

import java.net.URI;
import java.util.Objects;
import com.bank.yanki.model.DocumentType;
import com.bank.yanki.model.WalletStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.OffsetDateTime;
import java.util.Arrays;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.NoSuchElementException;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * WalletResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public class WalletResponse {

  private String id;

  private DocumentType documentType;

  private String documentNumber;

  private String phoneNumber;

  private String imei;

  private String email;

  private WalletStatus status;

  private Double balance;

  private JsonNullable<String> linkedDebitCardId = JsonNullable.<String>undefined();

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime createdAt;

  public WalletResponse id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public WalletResponse documentType(DocumentType documentType) {
    this.documentType = documentType;
    return this;
  }

  /**
   * Get documentType
   * @return documentType
  */
  @Valid 
  @Schema(name = "documentType", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("documentType")
  public DocumentType getDocumentType() {
    return documentType;
  }

  public void setDocumentType(DocumentType documentType) {
    this.documentType = documentType;
  }

  public WalletResponse documentNumber(String documentNumber) {
    this.documentNumber = documentNumber;
    return this;
  }

  /**
   * Get documentNumber
   * @return documentNumber
  */
  
  @Schema(name = "documentNumber", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("documentNumber")
  public String getDocumentNumber() {
    return documentNumber;
  }

  public void setDocumentNumber(String documentNumber) {
    this.documentNumber = documentNumber;
  }

  public WalletResponse phoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  /**
   * Get phoneNumber
   * @return phoneNumber
  */
  
  @Schema(name = "phoneNumber", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("phoneNumber")
  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public WalletResponse imei(String imei) {
    this.imei = imei;
    return this;
  }

  /**
   * Get imei
   * @return imei
  */
  
  @Schema(name = "imei", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("imei")
  public String getImei() {
    return imei;
  }

  public void setImei(String imei) {
    this.imei = imei;
  }

  public WalletResponse email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
  */
  @jakarta.validation.constraints.Email 
  @Schema(name = "email", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public WalletResponse status(WalletStatus status) {
    this.status = status;
    return this;
  }

  /**
   * Get status
   * @return status
  */
  @Valid 
  @Schema(name = "status", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("status")
  public WalletStatus getStatus() {
    return status;
  }

  public void setStatus(WalletStatus status) {
    this.status = status;
  }

  public WalletResponse balance(Double balance) {
    this.balance = balance;
    return this;
  }

  /**
   * Get balance
   * @return balance
  */
  
  @Schema(name = "balance", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("balance")
  public Double getBalance() {
    return balance;
  }

  public void setBalance(Double balance) {
    this.balance = balance;
  }

  public WalletResponse linkedDebitCardId(String linkedDebitCardId) {
    this.linkedDebitCardId = JsonNullable.of(linkedDebitCardId);
    return this;
  }

  /**
   * Get linkedDebitCardId
   * @return linkedDebitCardId
  */
  
  @Schema(name = "linkedDebitCardId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("linkedDebitCardId")
  public JsonNullable<String> getLinkedDebitCardId() {
    return linkedDebitCardId;
  }

  public void setLinkedDebitCardId(JsonNullable<String> linkedDebitCardId) {
    this.linkedDebitCardId = linkedDebitCardId;
  }

  public WalletResponse createdAt(OffsetDateTime createdAt) {
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
    WalletResponse walletResponse = (WalletResponse) o;
    return Objects.equals(this.id, walletResponse.id) &&
        Objects.equals(this.documentType, walletResponse.documentType) &&
        Objects.equals(this.documentNumber, walletResponse.documentNumber) &&
        Objects.equals(this.phoneNumber, walletResponse.phoneNumber) &&
        Objects.equals(this.imei, walletResponse.imei) &&
        Objects.equals(this.email, walletResponse.email) &&
        Objects.equals(this.status, walletResponse.status) &&
        Objects.equals(this.balance, walletResponse.balance) &&
        equalsNullable(this.linkedDebitCardId, walletResponse.linkedDebitCardId) &&
        Objects.equals(this.createdAt, walletResponse.createdAt);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, documentType, documentNumber, phoneNumber, imei, email, status, balance, hashCodeNullable(linkedDebitCardId), createdAt);
  }

  private static <T> int hashCodeNullable(JsonNullable<T> a) {
    if (a == null) {
      return 1;
    }
    return a.isPresent() ? Arrays.deepHashCode(new Object[]{a.get()}) : 31;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WalletResponse {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    documentType: ").append(toIndentedString(documentType)).append("\n");
    sb.append("    documentNumber: ").append(toIndentedString(documentNumber)).append("\n");
    sb.append("    phoneNumber: ").append(toIndentedString(phoneNumber)).append("\n");
    sb.append("    imei: ").append(toIndentedString(imei)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
    sb.append("    linkedDebitCardId: ").append(toIndentedString(linkedDebitCardId)).append("\n");
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

