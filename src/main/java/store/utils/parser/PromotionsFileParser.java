package store.utils.parser;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import store.domain.Promotion;
import store.utils.validator.ParserValidator;

public class PromotionsFileParser{

    public static Promotion getPromotions(List<String> promotion) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
            throw new IllegalArgumentException("[ERROR] 정수범위의 수량이 아닙니다.");
        }
    }

    public static int getGetQuantity(List<String> promotion) {
        ParserValidator.validateNumber(promotion.get(2));
        try {
            return Integer.parseInt(promotion.get(2));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 정수범위의 수량이 아닙니다.");
        }
    }

    public static LocalDateTime getStartTime(List<String> promotion, DateTimeFormatter formatter) {
        ParserValidator.validateDate(promotion.get(3));

        try {
            LocalDate startDateTime = LocalDate.parse(promotion.get(3), formatter);
            return startDateTime.atStartOfDay();
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("[ERROR] 날짜 형식이 올바르지 않습니다.");
        }
    }

    public static LocalDateTime getEndTime(List<String> promotion, DateTimeFormatter formatter) {
        ParserValidator.validateDate(promotion.getLast());
        try {
            LocalDate endDateTime = LocalDate.parse(promotion.getLast(), formatter);
            return endDateTime.atTime(23, 59, 59);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("[ERROR] 종료 날짜 형식이 올바르지 않습니다.");
        }
    }
}
