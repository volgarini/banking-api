package pt.com.bank.banking_api.factory.request;

import pt.com.bank.banking_api.dto.request.UpdateAccountRequest;
import pt.com.bank.banking_api.enums.AccountStatus;

public final class UpdateAccountRequestFactory {

    private UpdateAccountRequestFactory() {
    }

    public static UpdateAccountRequest create() {
        return new UpdateAccountRequest(AccountStatus.ACTIVE);
    }

    public static UpdateAccountRequest active() {
        return new UpdateAccountRequest(AccountStatus.ACTIVE);
    }

    public static UpdateAccountRequest blocked() {
        return new UpdateAccountRequest(AccountStatus.BLOCKED);
    }

    public static UpdateAccountRequest closed() {
        return new UpdateAccountRequest(AccountStatus.CLOSED);
    }

    public static UpdateAccountRequest withStatus(AccountStatus status) {
        return new UpdateAccountRequest(status);
    }
}
