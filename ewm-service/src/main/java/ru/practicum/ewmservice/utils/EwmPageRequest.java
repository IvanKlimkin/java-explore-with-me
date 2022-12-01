package ru.practicum.ewmservice.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class EwmPageRequest extends PageRequest {

    private int from;

    public EwmPageRequest(int from, int size, Sort sort) {
        super(from / size, size, sort);
        this.from = from;
    }

    @Override
    public long getOffset() {
        return from;
    }
}
