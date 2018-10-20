package com.example.demo.heroes;

import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class HeroControllerAdvice {
    /**
     * Exception thrown when Hero entity is not found in the repositiory.
     * @param ex: The error message is used.
     * @return HeroNotFoundException object.
     */
    @ResponseBody
    @ExceptionHandler(HeroNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    VndErrors HeroNotFoundExceptionHandler(HeroNotFoundException ex) {
        return new VndErrors("error", ex.getMessage());
    }
}
