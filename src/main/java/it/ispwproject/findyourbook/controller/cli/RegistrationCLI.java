package it.ispwproject.findyourbook.controller.cli;

import it.ispwproject.findyourbook.bean.RegistrationBean;
import it.ispwproject.findyourbook.controller.applicativo.RegistrationController;
import it.ispwproject.findyourbook.pattern.state.AbstractCLIState;
import it.ispwproject.findyourbook.pattern.state.CLIStateMachine;
import it.ispwproject.findyourbook.view.cli.RegistrationView;
import it.ispwproject.findyourbook.enumerator.Role;
import java.time.LocalDate;

public class RegistrationCLI extends AbstractCLIState {

    private final RegistrationController registrationController = new RegistrationController();
    private final RegistrationView view = new RegistrationView();

    @Override
    public void entry(CLIStateMachine context) {
        view.showHeader();
        // Aggiungi un piccolo suggerimento nella view o nel log se preferisci
        System.out.println("(Digita '0' in qualsiasi campo per annullare e tornare indietro)");
    }

    @Override
    public void action(CLIStateMachine context) {
        try {
            RegistrationBean bean = new RegistrationBean();

            String nome = view.askField("Nome");
            if (isBackChoice(nome)) { goBack(context); return; }
            bean.setName(nome);

            String cognome = view.askField("Cognome");
            if (isBackChoice(cognome)) { goBack(context); return; }
            bean.setSurname(cognome);

            String username = view.askField("Username");
            if (isBackChoice(username)) { goBack(context); return; }
            bean.setUsername(username);

            String email = view.askField("Email");
            if (isBackChoice(email)) { goBack(context); return; }
            bean.setEmail(email);

            String password = view.askPasswordField("Password");
            if (isBackChoice(password)) { goBack(context); return; }
            bean.setPassword(password);

            String confirm = view.askPasswordField("Conferma password");
            if (isBackChoice(confirm)) { goBack(context); return; }
            bean.setConfirmPassword(confirm);

            // 1. Chiedi prima il ruolo!
            Role role = view.askRole();
            bean.setRole(role);

            // 2. Ora chiedi i dati in base al ruolo
            if (role == Role.LETTORE) {
                String dataString = view.askField("Data di nascita (formato: AAAA-MM-GG)");
                if (isBackChoice(dataString)) { goBack(context); return; }
                try {
                    bean.setDataNascita(LocalDate.parse(dataString));
                } catch (Exception e) {
                    view.showError("Formato data non valido.");
                    goNext(context, this);
                    return;
                }
            } else {
                // Sei una Casa Editrice, chiedi la descrizione
                String desc = view.askField("Descrizione attività");
                if (isBackChoice(desc)) { goBack(context); return; }
                bean.setDescrizione(desc);
            }

            registrationController.register(bean);

            view.showSuccess("Registrazione completata!");
            goNext(context, new LoginCLI());

        } catch (Exception e) {
            view.showError("Errore: " + e.getMessage());
            goNext(context, this);
        }
    }
}