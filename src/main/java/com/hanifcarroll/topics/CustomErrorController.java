package com.hanifcarroll.topics;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object error = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        if (status != null) {
            int statusCode = Integer.valueOf(status.toString());
            String message;

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                message = "Page not found.";
            } else if (error.toString().contains("EntityNotFoundException")) {
                message = "This topic does not exist.";
            } else {
                message = "An error occurred.";
            }
            model.addAttribute("message", message);
        }
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
