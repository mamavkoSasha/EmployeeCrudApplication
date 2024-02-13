package com.example.demo.controllers;

import com.example.demo.exception.RestException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.example.demo.constants.Constants.Strings.WARNING_MESSAGE;

@Controller
public class CustomErrorController extends ResponseEntityExceptionHandler implements ErrorController {

    @ExceptionHandler(RestException.class)
    @ResponseBody
    public String handleRestException(RestException ex, Model model) {

        model.addAttribute(WARNING_MESSAGE, ex.getMessage()); // Set the warning message in the model

        return "new-employee"; // Replace "new-employee" with the name of your Thymeleaf template
    }

    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {

                return new ModelAndView("error-500");
            }
        }

        return new ModelAndView("custom-error");
    }

}
