package app.exception;


public class ReadingIsGoodException extends RuntimeException
{
    private ApiError error;

    public ReadingIsGoodException(ApiError error) {
        super();
        this.error = error;
    }
}
