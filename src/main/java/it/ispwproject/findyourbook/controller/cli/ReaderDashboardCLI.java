package it.ispwproject.findyourbook.controller.cli;

import it.ispwproject.findyourbook.pattern.state.AbstractCLIState;
import it.ispwproject.findyourbook.pattern.state.CLIStateMachine;
import it.ispwproject.findyourbook.view.cli.ReaderDashboardView; // Creeremo questa View tra un secondo
import it.ispwproject.findyourbook.util.logger.AppLogger;

public class ReaderDashboardCLI extends AbstractCLIState {

    private final ReaderDashboardView view = new ReaderDashboardView();

    @Override
    public void action(CLIStateMachine context) {
        view.showDashboardMenu();
        String choice = view.askChoice();

        switch (choice) {
            case "1" -> AppLogger.logInfo("-> Funzionalità Ricerca (Da implementare)");
            case "2" -> AppLogger.logInfo("-> Funzionalità Preferiti (Da implementare)");
            case "0" -> context.setState(null); // Logout
            default  -> {
                view.showError("Scelta non valida.");
                goNext(context, this);
            }
        }
    }
}