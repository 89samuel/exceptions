package br.com.dio.validator;

import br.com.dio.exception.ValidatorException;
import br.com.dio.model.UserModel;

public class UserValidator {

    private UserValidator(){

    }

    public static void VerifyModel(final UserModel model) throws ValidatorException {
         if (StringIsBlank(model.getName()))
             throw new ValidatorException("Informe um nome valido");
         if(model.getName().length() <= 1)
             throw new ValidatorException("O nome deve ter mais que 1 caractere");
        if (StringIsBlank(model.getEmail()))
            throw new ValidatorException("Informe um e-mail valido");
         if((!model.getEmail().contains("@")) || (!model.getEmail().contains(".")))
             throw new ValidatorException("Informe um e-mail valido");
    }

    private static boolean StringIsBlank(final String value){
        return value == null || value.isBlank();
    }
}
