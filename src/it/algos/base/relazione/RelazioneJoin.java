/**
 * Title:        RelazioneJoin.java
 * Package:      it.algos.base.relazione
 * Description:  Abstract Data Type per descrivere una clausola JOIN Sql
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 26 giugno 2003 alle 18.25
 */

package it.algos.base.relazione;

import it.algos.base.campo.base.Campo;

/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Definire un modello di dati che descrive una clausola JOIN Sql
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  26 giugno 2003 ore 18.25
 */
public final class RelazioneJoin extends Object {

    /* costanti statiche della classe */

    /**
     * Join di tipo LEFT JOIN - per adesso usiamo solo questa
     */
    public static String LEFT_JOIN = "LEFT";

    /**
     * Join di tipo INNER JOIN
     */
    public static String INNER_JOIN = "INNER";

    /**
     * Join di tipo RIGHT JOIN
     */
    public static String RIGHT_JOIN = "RIGHT";

    /**
     * tipo della JOIN (per adesso usiamo solo LEFT JOIN)
     */
    private String tipoJoin = "";

    /**
     * tavola di destinazione della JOIN
     */
    private String tavolaDestinazione = "";

    /**
     * nome chiave del campo sulla tavola destinazione
     */
    private String chiaveCampoDestinazione = "";

    /**
     * nome chiave del campo sulla tavola Partenza
     */
    private String chiaveCampoPartenza = "";

    /**
     * campo sulla tavola Partenza
     */
    private Campo campoPartenza;

    /**
     * campo sulla tavola Destinazione
     */
    private Campo campoDestinazione;


    /**
     * Costruttore da Relazione
     *
     * @param unaRelazione un oggetto di tipo Relazione
     */
    public RelazioneJoin(Relazione unaRelazione) {
        /** rimanda al costruttore della superclasse */
        super();

        /** regola le variabili di istanza coi parametri */
        this.tipoJoin = LEFT_JOIN;  //default
        this.tavolaDestinazione = unaRelazione.getTavolaArrivo();
        this.chiaveCampoDestinazione = unaRelazione.getChiaveCampoArrivo();
        this.chiaveCampoPartenza = unaRelazione.getChiaveCampoPartenza();
        this.setCampoPartenza(unaRelazione.getCampoPartenza());
        this.setCampoDestinazione(unaRelazione.getCampoArrivo());

    } /* fine del metodo costruttore completo */


    /**
     * Implementa la comparazione di due Join (this e un'altra)
     * Due Join sono uguali quando hanno gli stessi valori per tutti i campi
     */
    public boolean equals(Object altroOggetto) {

        //* Controlli preliminari per tutti gli operatori equals() */
        if (altroOggetto == this) {
            return true; // e' lo stesso oggetto
        }
        if (altroOggetto == null) {
            return false; // l'altro oggetto e' null
        }
        if (!(altroOggetto instanceof RelazioneJoin)) {
            return false; // tipi non comparabili
        }

        //* procedo alla comparazione */
        boolean uguali = false;
        RelazioneJoin questa = this;
        RelazioneJoin altra = (RelazioneJoin)altroOggetto;
        if (questa.getTipoJoin().equalsIgnoreCase(altra.getTipoJoin())) {
            if (questa.getTavolaDestinazione().equalsIgnoreCase(altra.getTavolaDestinazione())) {
                if (questa.getChiaveCampoDestinazione()
                        .equalsIgnoreCase(altra.getChiaveCampoDestinazione())) {
                    if (questa.getChiaveCampoPartenza()
                            .equalsIgnoreCase(altra.getChiaveCampoPartenza())) {
                        uguali = true;
                    } /* fine del blocco if */
                } /* fine del blocco if */
            } /* fine del blocco if */
        } /* fine del blocco if */

        return uguali;

    } /* fine del metodo */


    /**
     * Restituisce il tipo della Join
     */
    public String getTipoJoin() {
        return this.tipoJoin;
    } /* fine del metodo */


    /**
     * Restituisce la tavola destinazione
     */
    public String getTavolaDestinazione() {
        return this.tavolaDestinazione;
    } /* fine del metodo */


    /**
     * Restituisce il campo destinazione
     */
    public String getChiaveCampoDestinazione() {
        return this.chiaveCampoDestinazione;
    } /* fine del metodo */


    /**
     * Restituisce il campo destinazione
     */
    public String getChiaveCampoPartenza() {
        return this.chiaveCampoPartenza;
    } /* fine del metodo */


    public Campo getCampoPartenza() {
        return campoPartenza;
    }


    private void setCampoPartenza(Campo campoPartenza) {
        this.campoPartenza = campoPartenza;
    }


    public Campo getCampoDestinazione() {
        return campoDestinazione;
    }


    private void setCampoDestinazione(Campo campoDestinazione) {
        this.campoDestinazione = campoDestinazione;
    }

}// fine della classe