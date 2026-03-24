package com.bank.yanki.model;

import java.net.URI;
import java.util.Objects;
import com.bank.yanki.model.DocumentType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * CreateWalletRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.5.0")
public class CreateWalletRequest {

  private DocumentType documentType;

  private String documentNumber;

  private String phoneNumber;

  private String imei;

  private String email;

  public CreateWalletRequest() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CreateWalletRequest(DocumentType documentType, String documentNumber, String phoneNumber, String imei, String email) {
    this.documentType = documentType;
    this.documentNumber = documentNumber;
    this.phoneNumber = phoneNumber;
    this.imei = imei;
    this.email = email;
  }

  public CreateWalletRequest documentType(DocumentType documentType) {
    this.documentType = documentType;
    return this;
  }

  /**
   * Get documentType
   * @return documentType
  */
  @NotNull @Valid 
  @Schema(name = "documentType", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("documentType")
  public DocumentType getDocumentType() {
    return documentType;
  }

  public void setDocumentType(DocumentType documentType) {
    this.documentType = documentType;
  }

  public CreateWalletRequest documentNumber(String documentNumber) {
    this.documentNumber = documentNumber;
    return this;
  }

  /**
   * Get documentNumber
   * @return documentNumber
  */
  @NotNull 
  @Schema(name = "documentNumber", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("documentNumber")
  public String getDocumentNumber() {
    return documentNumber;
  }

  public void setDocumentNumber(String documentNumber) {
    this.documentNumber = documentNumber;
  }

  public CreateWalletRequest phoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
    return this;
  }

  /**
   * Número de celular (único)
   * @return phoneNumber
  */
  @NotNull 
  @Schema(name = "phoneNumber", description = "Número de celular (único)", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("phoneNumber")
  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public CreateWalletRequest imei(String imei) {
    this.imei = imei;
    return this;
  }

  /**
   * Get imei
   * @return imei
  */
  @NotNull 
  @Schema(name = "imei", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("imei")
  public String getImei() {
    return imei;
  }

  public void setImei(String imei) {
    this.imei = imei;
  }

  public CreateWalletRequest email(String email) {
    this.email = email;
    return this;
  }

  /**
   * Get email
   * @return email
  */
  @NotNull @jakarta.validation.constraints.Email 
  @Schema(name = "email", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateWalletRequest createWalletRequest = (CreateWalletRequest) o;
    return Objects.equals(this.documentType, createWalletRequest.documentType) &&
        Objects.equals(this.documentNumber, createWalletRequest.documentNumber) &&
        Objects.equals(this.phoneNumber, createWalletRequest.phoneNumber) &&
        Objects.equals(this.imei, createWalletRequest.imei) &&
        Objects.equals(this.email, createWalletRequest.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(documentType, documentNumber, phoneNumber, imei, email);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateWalletRequest {\n");
    sb.append("    documentType: ").append(toIndentedString(documentType)).append("\n");
    sb.append("    documentNumber: ").append(toIndentedString(documentNumber)).append("\n");
    sb.append("    phoneNumber: ").append(toIndentedString(phoneNumber)).append("\n");
    sb.append("    imei: ").append(toIndentedString(imei)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
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

