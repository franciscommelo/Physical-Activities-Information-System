package pt.isel.ls.handlers;


public interface CommandHandler {
    CommandResult execute(CommandRequest commandRequest) throws Exception;
}
