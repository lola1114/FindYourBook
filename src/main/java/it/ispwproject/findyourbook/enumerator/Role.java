package it.ispwproject.findyourbook.enumerator;

public enum Role {
    LETTORE,
    CASA_EDITRICE,
    AMMINISTRATORE;

    public static Role fromString(String role) {
        return switch (role.toUpperCase()) {
            case "LETTORE" -> LETTORE;
            case "CASA_EDITRICE" -> CASA_EDITRICE;
            case "AMMINISTRATORE" -> AMMINISTRATORE;
            default -> throw new IllegalArgumentException(
                    "Ruolo non valido: " + role);
        };
    }
}