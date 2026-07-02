package it.ispwproject.findyourbook.view.cli;

public class ReaderDashboardView {

    public void showDashboardMenu() {
        CLIRenderer.intestazione("FindYourBook – Pannello Lettore");
        CLIRenderer.voceMenu(1, "Cerca Libri");
        CLIRenderer.voceMenu(2, "I Miei Preferiti");
        CLIRenderer.voceMenuZero("Logout");
        CLIRenderer.separatore();
    }

    public String askChoice() {
        return CLIRenderer.chiediSceltaStringa("Scelta");
    }

    public void showError(String message) {
        CLIRenderer.errore(message);
    }
}