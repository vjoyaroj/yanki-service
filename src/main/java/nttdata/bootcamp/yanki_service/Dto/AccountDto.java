package nttdata.bootcamp.yanki_service.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Account projection for balance updates via accounts-service.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private String id;
    private String customerId;
    private String accountNumber;
    private String type;
    private Double balance;
    private String status;
    /** Required by accounts-service PUT /accounts/{id} (AccountUpdateRequest). */
    private List<String> authorizedSignatories;
}
