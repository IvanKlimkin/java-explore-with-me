package ru.practicum.ewmservice.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.user.dto.UserDto;
import ru.practicum.ewmservice.user.service.UserService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequestMapping(path = "/users/{userId}/rate/{ratedId}")
@RequiredArgsConstructor
public class UserLikeController {
    private final UserService userService;

    @PostMapping
    public UserDto rateUser(@PathVariable Long userId,
                            @PathVariable Long ratedId,
                            @Max(value = 5, message = "Maximum rate value is 5.")
                            @Min(value = 0, message = "Rating should be positive")
                            @RequestParam(name = "rating") Float rateValue) {
        return userService.rateUser(userId, ratedId, rateValue);
    }

    @PatchMapping
    public UserDto updateRateUser(@PathVariable Long userId,
                                  @PathVariable Long ratedId,
                                  @Max(value = 5, message = "Maximum rate value is 5.")
                                  @Min(value = 0, message = "Rating should be positive")
                                  @RequestParam(name = "rating") Float rateValue) {
        return userService.updateRateUser(userId, ratedId, rateValue);
    }

    @DeleteMapping
    public void deleteRate(@PathVariable Long userId,
                           @PathVariable Long ratedId) {
        userService.deleteRate(userId, ratedId);
    }
}
