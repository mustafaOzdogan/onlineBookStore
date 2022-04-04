package app.api.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class CreateBookRequest extends BaseApiRequest
{
    @NotBlank(message = "Book name is mandatory")
    private String bookName;

    @NotBlank(message = "Book name is mandatory")
    private String bookAuthor;
}
