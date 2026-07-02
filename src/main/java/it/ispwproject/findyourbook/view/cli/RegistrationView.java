package it.ispwproject.findyourbook.view.cli;

import it.ispwproject.findyourbook.enumerator.Role;

public class RegistrationView {

    public void showHeader() {
        CLIRenderer.intestazione("FindYourBook – Registrazione");
    }

    public String askField(String label) {
        return CLIRenderer.chiediCampo(label);
    }

    public String askPasswordField(String label) {
        return CLIRenderer.chiediCampo(label); // In futuro potresti usare una maschera
    }

    public Role askRole() {
        CLIRenderer.messaggio("Scegli il tuo ruolo:");
        CLIRenderer.voceMenu(1, "Lettore");
        CLIRenderer.voceMenu(2, "Casa Editrice");
        String choice = CLIRenderer.chiediSceltaStringa("Ruolo");
        return choice.equals("2") ? Role.CASA_EDITRICE : Role.LETTORE;
    }

    public void showSuccess(String message) {
        CLIRenderer.messaggio(CLIRenderer.OK + " " + message);
    }

    public void showError(String message) {
        CLIRenderer.errore(message);
    }
}