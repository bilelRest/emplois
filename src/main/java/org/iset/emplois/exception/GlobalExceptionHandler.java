package org.iset.emplois.exception;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


 @ExceptionHandler(MatiereExistanteException.class)
    public String handleMatiereExistanteException(MatiereExistanteException ex, Model model) {
        model.addAttribute("errornommatfr", ex.getMessage());
        return "parametres/matiere/form";
    }

 

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFoundException(ResourceNotFoundException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error/error";
    }

    @ExceptionHandler(BusinessException.class)
    public String handleBusinessException(BusinessException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, Model model) {
        model.addAttribute("error", "Une erreur inattendue s'est produite.");
        return "error/error";
    }
}