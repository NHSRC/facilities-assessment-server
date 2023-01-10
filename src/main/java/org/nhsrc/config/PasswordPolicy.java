package org.nhsrc.config;

import org.passay.*;
import org.springframework.stereotype.Component;

import java.util.List;

// https://www.passay.org/reference/
@Component
public class PasswordPolicy {
    private final PasswordValidator validator;

    public PasswordPolicy() {
        validator = new PasswordValidator(
                new LengthRule(12, 20),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1));
    }

    public RuleResult validate(String password) {
        return validator.validate(new PasswordData(password));
    }

    public String getMessage(RuleResult ruleResult) {
        List<String> messages = validator.getMessages(ruleResult);
        return String.join(";", messages);
    }
}
