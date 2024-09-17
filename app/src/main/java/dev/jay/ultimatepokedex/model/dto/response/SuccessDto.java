package dev.jay.ultimatepokedex.model.dto.response;

public class SuccessDto {
    private String message;

    public SuccessDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SuccessDto{" +
                "message='" + message + '\'' +
                '}';
    }
}
