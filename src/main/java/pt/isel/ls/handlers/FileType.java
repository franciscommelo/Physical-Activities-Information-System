package pt.isel.ls.handlers;

/**
 * Enum output type file format
 */
public enum FileType {
    PLAINTEXT("text/plain"),
    HTML("text/html"),
    JSON("application/json");

    private String text;

    FileType(String text) {
        this.text = text;
    }

    /**
     * @return String
     */
    public String getText() {
        return text;
    }

}
