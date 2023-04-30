package aplication.programming.nttdata.common.exception;

import org.springframework.http.HttpStatus;

public class NttdataError {

    public NttdataError() {
    }

    private static final String ERROR_CODE_001 = "NTT-001";
    private static final String ERROR_DESCRIPTION_001 = "Failed to get customer information";
    private static final String ERROR_CODE_002 = "NTT-002";
    private static final String ERROR_DESCRIPTION_002 = "Failed to create account";
    private static final String ERROR_CODE_003 = "NTT-003";
    private static final String ERROR_DESCRIPTION_003 = "Failed to check the accounts";
    private static final String ERROR_CODE_004 = "NTT-004";
    private static final String ERROR_DESCRIPTION_004 = "Failed to update account";
    private static final String ERROR_CODE_005 = "NTT-005";
    private static final String ERROR_DESCRIPTION_005 = "Failed to delete account";
    private static final String ERROR_CODE_006 = "NTT-006";
    private static final String ERROR_DESCRIPTION_006 = "Failed to create client";
    private static final String ERROR_CODE_007 = "NTT-007";
    private static final String ERROR_DESCRIPTION_007 = "Failed when consulting clients";
    private static final String ERROR_CODE_008 = "NTT-008";
    private static final String ERROR_DESCRIPTION_008 = "Failed to update client";
    private static final String ERROR_CODE_009 = "NTT-009";
    private static final String ERROR_DESCRIPTION_009 = "Failed to delete client";
    private static final String ERROR_CODE_010 = "NTT-010";
    private static final String ERROR_DESCRIPTION_010 = "Insufficient balance to carry out the transaction";
    private static final String ERROR_CODE_011 = "NTT-011";
    private static final String ERROR_DESCRIPTION_011 = "Failed to create movement";
    private static final String ERROR_CODE_012 = "NTT-012";
    private static final String ERROR_DESCRIPTION_012 = "The user not assigned to the account does not exist";

    public static final FailureException NTT001 = new FailureException(new Error(ERROR_CODE_001,ERROR_DESCRIPTION_001), HttpStatus.BAD_REQUEST.value());
    public static final FailureException NTT002 = new FailureException(new Error(ERROR_CODE_002,ERROR_DESCRIPTION_002), HttpStatus.INTERNAL_SERVER_ERROR.value());
    public static final FailureException NTT003 = new FailureException(new Error(ERROR_CODE_003,ERROR_DESCRIPTION_003), HttpStatus.INTERNAL_SERVER_ERROR.value());
    public static final FailureException NTT004 = new FailureException(new Error(ERROR_CODE_004,ERROR_DESCRIPTION_004), HttpStatus.INTERNAL_SERVER_ERROR.value());
    public static final FailureException NTT005 = new FailureException(new Error(ERROR_CODE_005,ERROR_DESCRIPTION_005), HttpStatus.INTERNAL_SERVER_ERROR.value());
    public static final FailureException NTT006 = new FailureException(new Error(ERROR_CODE_006,ERROR_DESCRIPTION_006), HttpStatus.INTERNAL_SERVER_ERROR.value());
    public static final FailureException NTT007 = new FailureException(new Error(ERROR_CODE_007,ERROR_DESCRIPTION_007), HttpStatus.INTERNAL_SERVER_ERROR.value());
    public static final FailureException NTT008 = new FailureException(new Error(ERROR_CODE_008,ERROR_DESCRIPTION_008), HttpStatus.INTERNAL_SERVER_ERROR.value());
    public static final FailureException NTT009 = new FailureException(new Error(ERROR_CODE_009,ERROR_DESCRIPTION_009), HttpStatus.INTERNAL_SERVER_ERROR.value());
    public static final FailureException NTT010 = new FailureException(new Error(ERROR_CODE_010,ERROR_DESCRIPTION_010), HttpStatus.INTERNAL_SERVER_ERROR.value());
    public static final FailureException NTT011 = new FailureException(new Error(ERROR_CODE_011,ERROR_DESCRIPTION_011), HttpStatus.INTERNAL_SERVER_ERROR.value());
    public static final FailureException NTT012 = new FailureException(new Error(ERROR_CODE_012,ERROR_DESCRIPTION_012), HttpStatus.INTERNAL_SERVER_ERROR.value());
}
