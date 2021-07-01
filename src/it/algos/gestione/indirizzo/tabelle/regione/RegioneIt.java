/**
 * Copyright(c): 2005
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 3-4-05
 */

package it.algos.gestione.indirizzo.tabelle.regione;

import it.algos.base.errore.Errore;


/**
 * Enumeration delle regioni italiane.
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 13-4-7
 */
public enum RegioneIt {

    abruzzo("Abruzzo", "ABR"),
    basilicata("Basilicata", "BAS"),
    calabria("Calabria", "CAL"),
    campania("Campania", "CAM"),
    emilia("Emilia-Romagna", "EMR"),
    friuli("Friuli-Venezia Giulia", "FVG"),
    lazio("Lazio", "LAZ"),
    liguria("Liguria", "LIG"),
    lombardia("Lombardia", "LOM"),
    marche("Marche", "MAR"),
    molise("Molise", "MOL"),
    piemonte("Piemonte", "PMN"),
    puglia("Puglia", "PUG"),
    sardegna("Sardegna", "SAR"),
    sicilia("Sicilia", "SIC"),
    toscana("Toscana", "TOS"),
    trentino("Trentino-Alto Adige", "TAA"),
    umbria("Umbria", "UMB"),
    aosta("Valle d'Aosta", "VAO"),
    veneto("Veneto", "VEN");

    /**
     * voce da utilizzare
     */
    private String titolo;

    /**
     * sigla secondo lo standard iso3166-2
     */
    private String sigla;


    /**
     * Costruttore completo con parametri.
     *
     * @param titolo utilizzato nei popup
     */
    RegioneIt(String titolo, String sigla) {
        try { // prova ad eseguire il codice
            /* regola le variabili di istanza coi parametri */
            this.setTitolo(titolo);
            this.setSigla(sigla);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public String getTitolo() {
        return titolo;
    }


    private void setTitolo(String titolo) {
        this.titolo = titolo;
    }


    public String getSigla() {
        return sigla;
    }


    private void setSigla(String sigla) {
        this.sigla = sigla;
    }
}// fine della classe
