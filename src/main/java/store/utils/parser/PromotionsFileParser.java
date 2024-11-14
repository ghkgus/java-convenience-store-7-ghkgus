package store.utils.parser;

import static store.constants.fileConstants.FileConstants.DATE_FORMAT;
import static store.constants.fileConstants.FileConstants.END_HOUR;
import static store.constants.fileConstants.FileConstants.END_MIN;
import static store.constants.fileConstants.FileConstants.END_SEC;
import static store.constants.fileConstants.FileErrorMessage.IS_NOT_CORRECT_DATE_FORM;
import static store.constants.fileConstants.FileErrorMessage.IS_NOT_INTEGER_RANGE;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import store.domain.Promotion;
import store.utils.validator.ParserValidator;

public class PromotionsFileParser{

    public static Promotion getPromotions(List<String> promotion) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

        String name = getName(promotion);
        int buyQuantity = getBuyQuantity(promotion);
        int getQuantity = getGetQuantity(promotion);
        LocalDateTime startTime = getStartTime(promotion, formatter);
        LocalDateTime endTime = getEndTime(promotion, formatter);

        return new Promotion(name, buyQuantity, getQuantity, startTime, endTime);
    }

    public static String getName(List<String> promotion) {
        ParserValidator.validateString(promotion.getFirst());
        return promotion.getFirst();
    }

    public static int getBuyQuantity(List<String> promotion) {
        ParserValidator.validateNumber(promotion.get(1));
        try {
            return Integer.parseInt(promotion.get(1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(IS_NOT_INTEGER_RANGE.getMessage());
        }
    }

    public static int getGetQuantity(List<String> promotion) {
        ParserValidator.validateNumber(promotion.get(2));
        try {
            return Integer.parseInt(promotion.get(2));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(IS_NOT_INTEGER_RANGE.getMessage());
        }
    }

    public static LocalDateTime getStartTime(List<String> promotion, DateTimeFormatter formatter) {
        ParserValidator.validateDate(promotion.get(3));

        try {
            LocalDate startDateTime = LocalDate.parse(promotion.get(3), formatter);
            return startDateTime.atStartOfDay();
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(IS_NOT_CORRECT_DATE_FORM.getMessage());
        }
    }

    public static LocalDateTime getEndTime(List<String> promotion, DateTimeFormatter formatter) {
        ParserValidator.validateDate(promotion.getLast());
        try {
            LocalDate endDateTime = LocalDate.parse(promotion.getLast(), formatter);
            return endDateTime.atTime(END_HOUR, END_MIN, END_SEC);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(IS_NOT_CORRECT_DATE_FORM.getMessage());
        }
    }
}
