package ru.practicum.ewmservice.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmservice.utils.Create;
import ru.practicum.ewmservice.utils.EwmPageRequest;
import ru.practicum.ewmservice.user.dto.UserDto;
import ru.practicum.ewmservice.user.service.UserService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getAll(@RequestParam(name = "ids", required = false) List<Long> ids,
                                @PositiveOrZero @RequestParam(
                                        name = "from", defaultValue = "0") Integer from,
                                @Positive @RequestParam(
                                        name = "size", defaultValue = "10") Integer size) {
        final EwmPageRequest pageRequest = new EwmPageRequest(from, size, Sort.unsorted());
        return userService.getAllUsersInList(ids, pageRequest);
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping
    public UserDto add(@Validated({Create.class}) @RequestBody UserDto userDto) {
        return userService.createNewUser(userDto);
    }

    @DeleteMapping("/{userId}")
    public void deleteItem(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

}
