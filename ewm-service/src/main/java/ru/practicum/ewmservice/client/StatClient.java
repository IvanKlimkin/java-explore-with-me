package ru.practicum.ewmservice.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewmservice.client.dto.RequestDto;
import ru.practicum.ewmservice.client.dto.ViewStats;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "statClient", url = "${EWM_STATS_SERVER_URL}")
public interface StatClient {

    @RequestMapping(method = RequestMethod.POST, value = "/hit", consumes = APPLICATION_JSON_VALUE)
    RequestDto saveRequest(@RequestBody RequestDto requestDto);

    @RequestMapping(method = RequestMethod.GET, value = "/stats", consumes = APPLICATION_JSON_VALUE)
    List<ViewStats> getStat(@RequestParam(name = "start")
                            String start,
                            @RequestParam(name = "end")
                            String end,
                            @RequestParam(name = "uris", required = false) List<String> uris,
                            @RequestParam(name = "unique", required = false, defaultValue = "false") Boolean unique);


}
