package br.com.dio.model;
public enum MenuOption {
    SAVE,
    UPDATE,
    DELETE,
    FIND_BY_ID,
    FIND_ALL,
    EXIT;

   /* MenuOption(final Consumer<UserDAO> callback) {
        this.callback = callback;
    }
    private Consumer<UserDAO> callback;

    public Runnable getCallback() {
        return callback;
    }*/
}
