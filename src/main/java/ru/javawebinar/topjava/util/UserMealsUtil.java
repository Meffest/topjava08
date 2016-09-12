package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed> filteredWithExceeded = getFilteredWithExceeded(mealList, LocalTime.of(12, 0), LocalTime.of(15, 0), 2000);
        for (UserMealWithExceed mealWithExceed : filteredWithExceeded) {
            System.out.println(mealWithExceed);
        }
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        Map<LocalDate, Integer> sumCaloriesPerDay = new HashMap<>();

        for (UserMeal meal : mealList) {
            sumCaloriesPerDay.put(meal.getDateTime().toLocalDate(), sumCaloriesPerDay.getOrDefault(meal.getDateTime().toLocalDate(), 0) + meal.getCalories());
        }

        List<UserMealWithExceed> userMealWithExceed = new ArrayList<>();

        for (UserMeal meal : mealList) {
            final LocalDateTime dateTime = meal.getDateTime();
            if (TimeUtil.isBetween(dateTime.toLocalTime(), startTime, endTime)) {
                userMealWithExceed.add(new UserMealWithExceed(dateTime, meal.getDescription(), meal.getCalories(), sumCaloriesPerDay.get(dateTime.toLocalDate()) > caloriesPerDay));
            }
        }
        return userMealWithExceed;
    }
}
