package pt.com.bank.banking_api.factory.constants;

import java.util.UUID;

public final class DocumentTypeTestConstants {

    private DocumentTypeTestConstants() {
    }

    public static final UUID DOCUMENT_TYPE_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    public static final String DEFAULT_DOCUMENT = "CPF";

    public static final String PASSPORT = "Passport";

    public static final String RESIDENCE_PERMIT = "Residence Permit";

    public static final String DEFAULT_DESCRIPTION = "Brasilian CPF";
}
