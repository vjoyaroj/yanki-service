package nttdata.bootcamp.yanki_service.Exception;

/**
 * Functional validation failure (HTTP 422) for wallet business rules.
 */
public class BusinessRuleException extends RuntimeException {
    /**
     * @param message detail for API clients
     */
    public BusinessRuleException(String message) {
        super(message);
    }
}
