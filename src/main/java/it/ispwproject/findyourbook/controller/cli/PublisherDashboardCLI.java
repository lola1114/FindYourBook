package it.ispwproject.findyourbook.controller.cli;

import it.ispwproject.findyourbook.pattern.state.AbstractCLIState;
import it.ispwproject.findyourbook.pattern.state.CLIStateMachine;
import it.ispwproject.findyourbook.view.cli.PublisherDashboardView; // Creeremo questa View

public class PublisherDashboardCLI extends AbstractCLIState {

    private final PublisherDashboardView view = new PublisherDashboardView();

    @Override
    public void action(CLIStateMachine context) {
        view.showDashboardMenu();
        String choice = view.askChoice();

        switch (choice) {
            case "1" -> System.out.println("-> Funzionalità Pubblica Libro (Da implementare)");
            case "2" -> System.out.println("-> Funzionalità Report (Da implementare)");
            case "0" -> context.setState(null); // Logout
            default  -> {
                view.showError("Scelta non valida.");
                goNext(context, this);
            }
        }
    }
}