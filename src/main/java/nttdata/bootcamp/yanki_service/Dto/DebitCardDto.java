package nttdata.bootcamp.yanki_service.Dto;

import lombok.Data;

import java.util.List;

/**
 * Debit card projection including linked bank accounts.
 */
@Data
public class DebitCardDto {
    private String id;
    private String customerId;
    private String cardNumber;
    private String status;
    private List<DebitCardAccountLinkDto> linkedAccounts;
}
