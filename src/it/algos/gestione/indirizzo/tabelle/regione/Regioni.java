package it.algos.gestione.indirizzo.tabelle.regione;

import it.algos.base.errore.Errore;

import java.util.ArrayList;


/**
 * Classe interna Enumerazione.
 */
public enum Regioni {

    abruzzo("Abruzzo", "Comuni dell'Abruzzo"),
    basilicata("Basilicata", "Comuni della Basilicata"),
    calabria("Calabria", "Comuni della Calabria"),
    campania("Campania", "Comuni della Campania"),
    emilia("Emilia-Romagna", "Comuni dell'Emilia-Romagna"),
    friuli("Friuli-Venezia Giulia", "Comuni del Friuli-Venezia Giulia"),
    lazio("Lazio", "Comuni del Lazio"),
    liguria("Liguria", "Comuni della Liguria"),
    lombardia("Lombardia", "Comuni della Lombardia"),
    marche("Marche", "Comuni delle Marche"),
    molise("Molise", "Comuni del Molise"),
    piemonte("Piemonte", "Comuni del Piemonte"),
    puglia("Puglia", "Comuni della Puglia"),
    sardegna("Sardegna", "Comuni della Sardegna"),
    sicilia("Sicilia", "Comuni della Sicilia"),
    toscana("Toscana", "Comuni della Toscana"),
    trentino("Trentino-Alto Adige", "Comuni del Trentino-Alto Adige"),
    umbria("Umbria", "Comuni dell'Umbria"),
    aosta("Valle d'Aosta", "Comuni della Valle d'Aosta"),
    veneto("Veneto", "Comuni del Veneto"),;

    /**
     * …
     * titolo da utilizzare
     */
    private String titolo;

    /**
     * categoria su wikipedia
     */
    private String categoria;


    /**
     * Costruttore completo con parametri.
     *
     * @param titolo utilizzato nei popup
     * @param categoria su wikipedia
     */
    Regioni(String titolo, String categoria) {
        try { // prova ad eseguire il codice
            /* regola le variabili di istanza coi parametri */
            this.setTitolo(titolo);
            this.setCategoria(categoria);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public static ArrayList<Regioni> getLista() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Regioni> lista = null;

        try { // prova ad eseguire il codice
            lista = new ArrayList<Regioni>();

            /* traverso tutta la collezione */
            for (Regioni op : Regioni.values()) {
                lista.add(op);
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    public static Regioni get(int pos) {
        /* variabili e costanti locali di lavoro */
        Regioni reg = null;
        Regioni[] set;

        try { // prova ad eseguire il codice

            /* traverso tutta la collezione */
            set = Regioni.values();

            if (set != null) {
                reg = set[pos - 1];
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return reg;
    }


    @Override public String toString() {
        return this.getTitolo();
    }


    public String getTitolo() {
        return titolo;
    }


    private void setTitolo(String titolo) {
        this.titolo = titolo;
    }


    public String getCategoria() {
        return categoria;
    }


    private void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}// fine della classe
