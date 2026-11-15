import br.com.dio.dao.UserDAO;
import br.com.dio.exception.EmptyStorageException;
import br.com.dio.exception.UserNotFoundException;
import br.com.dio.exception.ValidatorException;
import br.com.dio.model.MenuOption;
import br.com.dio.model.UserModel;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static br.com.dio.validator.UserValidator.VerifyModel;

public class Main {

    private final static UserDAO dao = new UserDAO();
    private final  static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while(true){
            System.out.println("Bem vindo ao cadastro de usuarios, selecione a operação desejada");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Atualizar");
            System.out.println("3 - Excluir");
            System.out.println("4 - Buscar por identificador");
            System.out.println("5 - Listar");
            System.out.println("6 - Sair");
            var userInput = scanner.nextInt();
            var selectedOption = MenuOption.values()[userInput -1];
            switch (selectedOption){
                case SAVE -> {
                    try {
                        var user = dao.save(requestToSave());
                        System.out.printf("Usuario cadastrado %s", user);
                    }catch (ValidatorException ex){
                        System.out.println(ex.getMessage());
                        ex.printStackTrace();
                    }
                }
                case UPDATE -> {
                    try{
                      var user = dao.update(requestToUpdate());
                      System.out.printf("Usuario atualizado %s", user);
                     }catch(UserNotFoundException | EmptyStorageException ex) {
                        System.out.println(ex.getMessage());
                    }catch(ValidatorException ex) {
                        System.out.println(ex.getMessage());
                        ex.printStackTrace();
                    }finally{
                        System.out.println("==================================");
                    }
                }
                case DELETE -> {
                    try{
                       dao.delete(requestId());
                       System.out.printf("Usuario excluido");
                      }catch(UserNotFoundException | EmptyStorageException ex){
                        System.out.println(ex.getMessage());
                    }finally{
                        System.out.println("==================================");
                    }
                }
                case FIND_BY_ID -> {
                    try {
                        var id = requestId();
                        var user = dao.findById(id);
                        System.out.printf("Usuario cadastrado: com id %s", id);
                        System.out.println(user);
                    }catch(UserNotFoundException | EmptyStorageException ex){
                        System.out.println(ex.getMessage());
                    }finally{
                    System.out.println("==================================");
                   }
                }
                case FIND_ALL -> {
                    var users = dao.findAll();
                    System.out.printf("Usuario cadastrados");
                    System.out.printf("===================");
                    users.forEach(System.out::println);
                    System.out.printf("===================");
                }
                case EXIT -> System.exit(0);
                }
            }
        }

        private static long requestId() {
            System.out.println("Informe o identificador do usuario");
            return scanner.nextLong();
        }

    private static UserModel requestToSave() throws ValidatorException {
        System.out.println("Informe o nome do usuario: ");
        var name = scanner.next();
        System.out.println("Informe o e-mail do usuario: ");
        var email = scanner.next();
        System.out.println("Informe a data de nsacimento do usuario (dd/MM/yyyy): ");
        var birthdayString = scanner.next();
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        var birthday = LocalDate.parse(birthdayString, formatter);
        return validateInputs(0, name, email, birthday);
    }

    private static UserModel validateInputs(final long id, final String name,
                               final String email, final LocalDate birthday) throws ValidatorException {
        var user = new UserModel(0, name, email, birthday);
        VerifyModel(user);
        return user;
    }

    private static UserModel requestToUpdate() throws ValidatorException {
        System.out.println("Informe o identificador do usuario: ");
        var id = scanner.nextLong();
        System.out.println("Informe o nome do usuario: ");
        var name = scanner.next();
        System.out.println("Informe o e-mail do usuario: ");
        var email = scanner.next();
        System.out.println("Informe a data de nsacimento do usuario (dd/MM/yyyy): ");
        var birthdayString = scanner.next();
        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        var birthday = OffsetDateTime.parse(birthdayString, formatter);
        return validateInputs(0, name, email, birthday.toLocalDate());
    }
}
