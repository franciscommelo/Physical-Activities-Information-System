package pt.isel.ls.handlers.commands;

import pt.isel.ls.handlers.CommandHandler;
import pt.isel.ls.handlers.CommandRequest;
import pt.isel.ls.handlers.CommandResult;
import java.util.Map;

public class Option implements CommandHandler {
    /**
     * Execute option command results
     *
     * @param commandRequest requested command
     * @return CommandResult
     * @throws Exception if not found
     */
    @Override
    public CommandResult execute(CommandRequest commandRequest) throws Exception {

        StringBuilder result = new StringBuilder();

        result.append("Presenting available commands... \n");

        for (Map.Entry<String, String> entry : commandRequest.getRouter().getRoutesDesc().entrySet()) {
            result.append(entry.getKey() + " -> " + entry.getValue());
            result.append("\n");
        }
        return new CommandResult(result);
    }
}

