package nttdata.bootcamp.yanki_service.Dto;

import lombok.Data;

/**
 * Link between a debit card and a bank account id.
 */
@Data
public class DebitCardAccountLinkDto {
    private String accountId;
    private Boolean isPrimary;
}
