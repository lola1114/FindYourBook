package it.ispwproject.findyourbook.view.cli;

public class PublisherDashboardView {

    public void showDashboardMenu() {
        CLIRenderer.intestazione("FindYourBook – Pannello Editore");
        CLIRenderer.voceMenu(1, "Pubblica Nuovo Libro");
        CLIRenderer.voceMenu(2, "Report e Vendite");
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