package com.usermanagement.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommonUtils {

    private final ModelMapper modelMapper;

    public static <E extends Throwable> void isNullThrow(Object data, Supplier<? extends E> ex) throws E {
        if (Objects.isNull(data)) {
            throw ex.get();
        }
    }

    public <T, D> D convertTo(final T source, final Class<D> destination) {
        return this.modelMapper.map(source, destination);
    }
    public <T, D> D convertTo(final T source, final Type destination) {
        return this.modelMapper.map(source,destination);
    }
    //new TypeToken<List<Character>>() {}.getType()

    public static LocalDateTime responseTime(){
        return LocalDateTime.now();
    }
}
