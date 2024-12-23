package org.seclore.util;

import lombok.extern.slf4j.Slf4j;
import org.seclore.model.Customer;

import java.util.regex.Pattern;

@Slf4j
public class DocumentMatcher {

    public static boolean matchDetails(Customer customer, String text) {
        Pattern namePattern = Pattern.compile(customer.getName(), Pattern.CASE_INSENSITIVE);
        boolean isNameMatched = findText(text, namePattern);
        log.info("Name: " + isNameMatched);
        return isNameMatched;
    }

    private static boolean findText(String text, Pattern pattern) {
        return pattern.matcher(text).find();
    }
}
