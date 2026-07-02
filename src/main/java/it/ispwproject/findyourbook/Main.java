package it.ispwproject.findyourbook;

import it.ispwproject.findyourbook.controller.cli.ModeSelectorCLI;
import it.ispwproject.findyourbook.controller.gui.MainGUI;
import it.ispwproject.findyourbook.dao.ConnectionFactory;
import it.ispwproject.findyourbook.pattern.state.CLIStateMachineImpl;
import it.ispwproject.findyourbook.view.cli.CLIRenderer;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("🚀 Avvio di FindYourBook in corso...");
        // Mantengo il tuo test del DB (ottima pratica)
        try {
            ConnectionFactory.getConnection();
            System.out.println("✅ Connessione al database riuscita!");
        } catch (Exception e) {
            System.err.println("⚠️ Attenzione: Impossibile connettersi al database!");
        }

        // 1. Selezione Modalità Database
        ModeSelectorCLI modeSelector = new ModeSelectorCLI();
        boolean proceed = modeSelector.start();
        if (!proceed) return;

        // 2. Selezione Interfaccia
        Scanner scanner = new Scanner(System.in);
        String scelta = "";

        while (!scelta.equals("1") && !scelta.equals("2")) {
            CLIRenderer.sezione("Seleziona interfaccia");
            CLIRenderer.voceMenu(1, "CLI  — interfaccia testuale");
            CLIRenderer.voceMenu(2, "GUI  — interfaccia grafica");
            scelta = CLIRenderer.chiediSceltaStringa("Scelta");

            if (!scelta.equals("1") && !scelta.equals("2")) {
                CLIRenderer.errore("Scelta non valida.");
            }
        }

        // 3. Avvio
        if (scelta.equals("2")) {
            MainGUI.launch(args); // Lancia JavaFX
        } else {
            // Lancia la Macchina a Stati per il Terminale
            new CLIStateMachineImpl().start();
        }
    }
}