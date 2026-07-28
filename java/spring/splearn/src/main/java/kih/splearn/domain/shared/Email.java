package kih.splearn.domain.shared;

import jakarta.persistence.Column;

import java.util.regex.Pattern;

public record Email(@Column(name="email_address", length = 150, nullable = false) String address) {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    public Email{

        if(!EMAIL_PATTERN.matcher(address).matches()){
            throw new IllegalStateException("invalid email format: "+ address);
        }
    }
}
