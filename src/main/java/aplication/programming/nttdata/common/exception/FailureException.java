package aplication.programming.nttdata.common.exception;

import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Generated
@RequiredArgsConstructor
public class FailureException extends RuntimeException {
    private final Error error;
    private final int errorCode;
}
