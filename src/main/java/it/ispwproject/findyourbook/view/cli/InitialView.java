package it.ispwproject.findyourbook.view.cli;

public class InitialView {

    public void showWelcome() {
        CLIRenderer.vuota();
        System.out.println(CLIRenderer.LINE_DECO);
        System.out.println(CLIRenderer.centra("F I N D  Y O U R  B O O K"));
        System.out.println(CLIRenderer.centra("La tua libreria a portata di terminale!"));
        System.out.println(CLIRenderer.LINE_DECO);
    }

    public void showMenu() {
        CLIRenderer.vuota();
        CLIRenderer.voceMenu(1, "Accedi");
        CLIRenderer.voceMenu(2, "Registrati");
        CLIRenderer.voceMenuZero("Esci");
    }

    public String askChoice() {
        return CLIRenderer.chiediSceltaStringa("Scelta");
    }

    public void showError(String message) {
        CLIRenderer.errore(message);
    }
}