/**
 * Title:     Cam
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      3-giu-2009
 */
package it.algos.albergo.statogiornaliero;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.wrapper.Campi;

import java.util.ArrayList;

/**
 * Classe interna Enumerazione.
 * <p/>
 * Codifica dei nomi dei campi<br>
 */
enum Cam implements Campi {


    codperiodo("codperiodo", "", "", "", false, false),
    codcamera("codcamera", "", "", "", false, false),
    camera("camera", "cam", "", "", true, true),
    libera("libera", "", "", "", false, false),
    cliente("cliente", "", "", "", true, true),
    adulti("adulti", "ad", "", "", true, true),
    bambini("bambini", "ba", "", "", true, true),
    trattamento("trattamento", "trat", "", "", true, true),
    lunchA("lunchA", "lun-A", "", "", true, true),
    lunchB("lunchB", "lun-B", "", "", true, true),
    lunchBoh("lunchBoh", "", "", "", false,false),
    dinnerA("dinnerA", "din-A", "", "", true, true),
    dinnerB("dinnerB", "din-B", "", "", true, true),
    dinnerBoh("dinnerBoh", "", "", "", false,false),
    arrivo("arrivo", "arr", "", "", true, true),
    cambio("cambio", "cam", "", "", true, true),
    partenza("partenza", "par", "", "", true, true),
    codArrPar("codarrpar", "con", "", "", true, true),
    apConfermata("apconfermata", "conf", "conf", "", true, true),
    cambio_da_a("cambio_da_a", "da/a", "", "", true, true),
    preparazione("preparazione", "prep", "", "", true, true),
    note("note", "", "", "", true, true);

    /**
     * nome interno del campo usato nel database.
     * <p/>
     * default il nome minuscolo della Enumeration <br>
     */
    private String nomeInterno;

    /**
     * titolo della colonna della lista.
     * <p/>
     * default il nome del campo <br>
     */
    private String titoloColonna;

    /**
     * titolo della etichetta in scheda.
     * <p/>
     * default il nome del campo <br>
     */
    private String etichettaScheda;

    /**
     * legenda del campo nella scheda.
     * <p/>
     * nessun default <br>
     */
    private String legenda;

    /**
     * flag per la visibilità nella lista.
     * <p/>
     * nessun default <br>
     */
    private boolean visibileLista;

    /**
     * flag - true se il campo è esportabile.
     * <p/>
     * default = true
     */
    private boolean export;



    /**
     * Costruttore completo con parametri.
     *
     * @param nomeInterno interno del campo usato nel database
     * @param titoloColonna titolo della colonna della lista
     * @param etichettaScheda titolo dell'etichetta nella scheda
     * @param legenda legenda del campo nella scheda
     * @param visibileLista flag per la visibilità nella lista
     * @param export flag per l'esportabilità del campo
     */
    Cam(
            String nomeInterno,
            String titoloColonna,
            String etichettaScheda,
            String legenda,
            boolean visibileLista,
            boolean export) {

        try { // prova ad eseguire il codice
            /* regola le variabili di istanza coi parametri */
            this.setNome(nomeInterno);
            this.setTitoloColonna(titoloColonna);
            this.setEtichettaScheda(etichettaScheda);
            this.setLegenda(legenda);
            this.setVisibileLista(visibileLista);
            this.setExport(export);

            /* controllo automatico che ci sia il nome interno */
            if (Lib.Testo.isVuota(nomeInterno)) {
                this.setNome(this.toString());
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ritorna un array con i campi esportabili.
     * <p/>
     * @return un array con i campi esportabili
     */
    public static Cam[] getCampiEsportabili () {
        /* variabili e costanti locali di lavoro */
        Cam[] esportabili  = new Cam[0];

        try {    // prova ad eseguire il codice
            ArrayList<Cam> lista = new ArrayList<Cam>();
            for(Cam c : Cam.values()){
                if (c.isExport()) {
                    lista.add(c);
                }// fine del blocco if
            }

            /* crea array dalla lista */
            esportabili = new Cam[lista.size()];
            for (int k = 0; k < lista.size(); k++) {
                esportabili[k]=lista.get(k);
            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return esportabili;
    }





    public String get() {
        return nomeInterno;
    }


    public String getNome() {
        return nomeInterno;
    }


    private void setNome(String nomeInterno) {
        this.nomeInterno = nomeInterno;
    }


    public String getTitoloColonna() {
        return titoloColonna;
    }


    private void setTitoloColonna(String titoloColonna) {
        this.titoloColonna = titoloColonna;
    }


    public String getEtichettaScheda() {
        return etichettaScheda;
    }


    private void setEtichettaScheda(String etichettaScheda) {
        this.etichettaScheda = etichettaScheda;
    }


    public String getLegenda() {
        return legenda;
    }


    private void setLegenda(String legenda) {
        this.legenda = legenda;
    }


    public boolean isVisibileLista() {
        return visibileLista;
    }


    private void setVisibileLista(boolean visibileLista) {
        this.visibileLista = visibileLista;
    }


    private boolean isExport() {
        return export;
    }


    private void setExport(boolean export) {
        this.export = export;
    }
}// fine della Enumeration Cam

