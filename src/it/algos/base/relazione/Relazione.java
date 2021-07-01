/**
 * Title:        Relazione.java
 * Package:      it.algos.base.progetto
 * Description:  Abstract Data Types per le relazioni del database
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 20 giugno 2003 alle 17.39
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------

package it.algos.base.relazione;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.db.CampoDB;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;

/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Definire un modello di dati che descrive la relazione tra
 * due tavole in un database
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  20 giugno 2003 ore 17.39
 */
public final class Relazione extends Object {

    /**
     * Nome chiave del modulo di partenza
     */
    private String nomeModuloPartenza = null;

    /**
     * Nome chiave del modulo di arrivo
     */
    private String nomeModuloArrivo = null;

    /**
     * nome interno del campo sulla tavola Partenza
     */
    private String nomeCampoPartenza = null;

    /**
     * nome interno del campo sulla tavola Arrivo
     */
    private String nomeCampoArrivo = null;

    /**
     * nome chiave del campo sulla tavola Partenza
     */
    private String chiaveCampoPartenza = null;

    /**
     * nome chiave del campo sulla tavola Arrivo
     */
    private String chiaveCampoArrivo = null;

    /**
     * flag per indicare se la relazione e' preferita
     * (vedi note in CDBLinkato)
     */
    private boolean isRelazionePreferita = false;

    /**
     * Campo di partenza
     */
    private Campo campoPartenza;

    /**
     * Campo di arrivo
     */
    private Campo campoArrivo;


    /**
     * Costruttore completo con Campo.
     * <p/>
     *
     * @param campo l'oggetto Campo per il quale costruire la Relazione
     */
    public Relazione(Campo campo) {
        /* variabili e costanti locali di lavoro */
        Campo campoPartenza = null;
        Campo campoArrivo = null;
        String nomeModuloPartenza = null;
        String nomeCampoPartenza = null;
        String nomeModuloArrivo = null;
        String nomeCampoArrivo = null;
        boolean preferita = false;
        CampoDB campoDbPartenza = null;

        try { // prova ad eseguire il codice
            campoPartenza = campo;
            campoDbPartenza = campoPartenza.getCampoDB();
            campoArrivo = campoDbPartenza.getCampoLinkChiave();
            nomeModuloPartenza = campoPartenza.getModulo().getNomeChiave();
            nomeCampoPartenza = campoPartenza.getNomeInterno();
            nomeModuloArrivo = campoDbPartenza.getModuloLinkato().getNomeChiave();
            nomeCampoArrivo = campoArrivo.getNomeInterno();
            preferita = campoDbPartenza.isRelazionePreferita();

            this.setNomeModuloPartenza(nomeModuloPartenza);
            this.setNomeCampoPartenza(nomeCampoPartenza);
            this.setNomeModuloArrivo(nomeModuloArrivo);
            this.setNomeCampoArrivo(nomeCampoArrivo);
            this.setCampoPartenza(campoPartenza);
            this.setCampoArrivo(campoArrivo);
            this.setRelazionePreferita(preferita);

            this.setChiaveCampoPartenza(campoPartenza.getChiaveCampo());
            this.setChiaveCampoArrivo(campoArrivo.getChiaveCampo());

            this.inizia();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /** variabili e costanti locali di lavoro */
        boolean esegui = false;

        // Controlla che tutti i campi obbligatori siano riempiti
        esegui = false;
        if (Lib.Testo.isValida(this.getNomeModuloPartenza())) {
            if (Lib.Testo.isValida(this.getNomeModuloArrivo())) {
                if (Lib.Testo.isValida(this.getNomeCampoPartenza())) {
                    if (Lib.Testo.isValida(this.getNomeCampoArrivo())) {
                        esegui = true;
                    } /* fine del blocco if-else */
                } /* fine del blocco if-else */
            } /* fine del blocco if-else */
        } /* fine del blocco if-else */

        // se tutto e' ok costruisce la relazione, altrimenti solleva una eccezione
        if (esegui) {
//            // todo da eliminare, vedere dove usato!
//            this.setNomeSqlCampoPartenza(
//                    Libreria.nomeCompletoSqlCampo(
//                            this.getTavolaPartenza(), this.getNomeCampoPartenza()));
//            this.setNomeSqlCampoArrivo(
//                    Libreria.nomeCompletoSqlCampo(
//                            this.getTavolaArrivo(), this.getNomeCampoArrivo()));


        } else {
            throw new Exception("MessaggioRelazioneNonInstaurabile");
        } /* fine del blocco if-else */

    }


    /**
     * Implementa la comparazione di due Relazioni (this e un'altra)
     * Due relazioni sono uguali quando hanno gli stessi valori per i campi:
     * tavolaPartenza, tavolaArrivo, campo Partenza, campo Arrivo (nomi interni)
     *
     * @param altroOggetto la Relazione da verificare rispetto a questa (this)
     *
     * @return true se le due relazioni sono uguali
     */
    public boolean equals(Object altroOggetto) {

        //* Controlli preliminari per tutti gli operatori equals() */
        if (altroOggetto == this) {
            return true; // e' lo stesso oggetto
        }
        if (altroOggetto == null) {
            return false; // l'altro oggetto e' null
        }
        if (!(altroOggetto instanceof Relazione)) {
            return false; // tipi non comparabili
        }

        //* procedo alla comparazione */
        boolean uguali = false;
        Relazione questa = this;
        Relazione altra = (Relazione)altroOggetto;
        if (questa.getTavolaArrivo().equalsIgnoreCase(altra.getTavolaArrivo())) {
            if (questa.getTavolaPartenza().equalsIgnoreCase(altra.getTavolaPartenza())) {
                if (questa.getNomeCampoArrivo().equalsIgnoreCase(altra.getNomeCampoArrivo())) {
                    if (questa.getNomeCampoPartenza()
                            .equalsIgnoreCase(altra.getNomeCampoPartenza())) {
                        uguali = true;
                    } /* fine del blocco if */
                } /* fine del blocco if */
            } /* fine del blocco if */
        } /* fine del blocco if */

        return uguali;

    } /* fine del metodo */


    /**
     * Implementa la comparazione di due Relazioni (this e un'altra)
     * Le relazioni si considerano uguali quando uniscono le stesse due tavole:
     * tavolaPartenza, tavolaArrivo.
     *
     * @param altroOggetto la Relazione da verificare rispetto a questa (this)
     *
     * @return true se le due relazioni uniscono le stesse due tavole
     */
    public boolean equalsTavole(Object altroOggetto) {

        //* Controlli preliminari per tutti gli operatori equals() */
        if (altroOggetto == this) {
            return true; // e' lo stesso oggetto
        }
        if (altroOggetto == null) {
            return false; // l'altro oggetto e' null
        }
        if (!(altroOggetto instanceof Relazione)) {
            return false; // tipi non comparabili
        }

        //* procedo alla comparazione */
        boolean uguali = false;
        Relazione questa = this;
        Relazione altra = (Relazione)altroOggetto;
        if (questa.getTavolaArrivo().equalsIgnoreCase(altra.getTavolaArrivo())) {
            if (questa.getTavolaPartenza().equalsIgnoreCase(altra.getTavolaPartenza())) {
                uguali = true;
            } /* fine del blocco if */
        } /* fine del blocco if */

        return uguali;

    } /* fine del metodo */


    /**
     * Ritorna una rappresentazione in formato String della relazione
     */
    public String toString() {
        String unaStringa = "";
        unaStringa += this.getTavolaPartenza();
        unaStringa += ".";
        unaStringa += this.getNomeCampoPartenza();
        unaStringa += " -> ";
        unaStringa += this.getTavolaArrivo();
        unaStringa += ".";
        unaStringa += this.getNomeCampoArrivo();
        return unaStringa;
    } /* fine del metodo */


    /**
     * Ritorna la tavola di partenza della Relazione.
     * <p/>
     *
     * @return la tavola dalla quale la relazione parte (many table)
     */
    public String getTavolaPartenza() {
        /* variabili e costanti locali di lavoro */
        String tavola = null;
        Modulo modulo = null;
        Campo campoPartenza;

        try { // prova ad eseguire il codice
            campoPartenza = this.getCampoPartenza();
            if (campoPartenza != null) {
                modulo = campoPartenza.getModulo();
                if (modulo != null) {
                    tavola = modulo.getTavola().toLowerCase();
                }// fine del blocco if
            }// fine del blocco if

//            modulo = Progetto.getModulo(this.getNomeModuloPartenza());
//            if (modulo != null) {
//                tavola = modulo.getTavola().toLowerCase();
//            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return tavola;
    }


    /**
     * Ritorna la tavola di arrivo della Relazione.
     * <p/>
     *
     * @return la tavola alla quale la relazione arriva (one table)
     */
    public String getTavolaArrivo() {
        /* variabili e costanti locali di lavoro */
        String tavola = null;
        Modulo modulo = null;

        try { // prova ad eseguire il codice

            modulo = Progetto.getModulo(this.getNomeModuloArrivo());
            if (modulo != null) {
                tavola = modulo.getModello().getTavolaArchivio().toLowerCase();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return tavola;
    }


    /**
     * Ritorna il modulo di partenza della relazione.
     * <p/>
     *
     * @return il modulo di arrivo
     */
    public Modulo getModuloPartenza() {
        return Progetto.getModulo(this.getNomeModuloPartenza());
    }


    /**
     * Ritorna il modulo di arrivo della relazione.
     * <p/>
     *
     * @return il modulo di arrivo
     */
    public Modulo getModuloArrivo() {
        return Progetto.getModulo(this.getNomeModuloArrivo());
    }


    /**
     * Ritorna il nome chiave del modulo di partenza.
     * <p/>
     *
     * @return il nome chiave del modulo di partenza.
     */
    public String getNomeModuloPartenza() {
        return nomeModuloPartenza;
    }


    /**
     * Regola il nome chiave del modulo di partenza.
     * <p/>
     *
     * @param nomeModuloPartenza il nome chiave del modulo di partenza.
     */
    private void setNomeModuloPartenza(String nomeModuloPartenza) {
        this.nomeModuloPartenza = nomeModuloPartenza;
    }


    /**
     * Ritorna il nome chiave del modulo di arrivo.
     * <p/>
     *
     * @return il nome chiave del modulo di arrivo.
     */
    public String getNomeModuloArrivo() {
        return nomeModuloArrivo;
    }


    /**
     * Regola il nome chiave del modulo di arrivo.
     * <p/>
     *
     * @param nomeModuloArrivo il nome chiave del modulo di arrivo.
     */
    private void setNomeModuloArrivo(String nomeModuloArrivo) {
        this.nomeModuloArrivo = nomeModuloArrivo;
    }


    /**
     * Ritorna il nome interno del campo di partenza.
     * <p/>
     *
     * @return il nome interno del campo di partenza
     */
    public String getNomeCampoPartenza() {
        return this.nomeCampoPartenza;
    }


    /**
     * Regola il nome interno del campo di partenza.
     * <p/>
     *
     * @param nomeCampoPartenza il nome interno del campo di partenza.
     */
    private void setNomeCampoPartenza(String nomeCampoPartenza) {
        this.nomeCampoPartenza = nomeCampoPartenza;
    }


    /**
     * Ritorna il nome interno del campo di arrivo.
     * <p/>
     *
     * @return il nome interno del campo di arrivo
     */
    public String getNomeCampoArrivo() {
        return this.nomeCampoArrivo;
    }


    /**
     * Regola il nome interno del campo di arrivo.
     * <p/>
     *
     * @param nomeCampoArrivo il nome interno del campo di arrivo.
     */
    private void setNomeCampoArrivo(String nomeCampoArrivo) {
        this.nomeCampoArrivo = nomeCampoArrivo;
    }


    /**
     * Ritorna il flag Relazione Preferita.
     * <p/>
     *
     * @return true se la relazione e' preferita, false se non lo e'
     */
    public boolean isRelazionePreferita() {
        return this.isRelazionePreferita;
    }


    /**
     * Regola il flag Relazione Preferita.
     * <p/>
     *
     * @param flag true se e' preferita, false se non lo e'
     */
    private void setRelazionePreferita(boolean flag) {
        this.isRelazionePreferita = flag;
    }


    /**
     * Ritorna il nome chiave del campo di partenza.
     * <p/>
     *
     * @return il nome chiave del campo di partenza.
     */
    public String getChiaveCampoPartenza() {
        return this.chiaveCampoPartenza;
    }


    /**
     * Regola il nome chiave del campo di partenza.
     * <p/>
     *
     * @param chiaveCampoPartenza il nome chiave del campo di partenza
     */
    private void setChiaveCampoPartenza(String chiaveCampoPartenza) {
        this.chiaveCampoPartenza = chiaveCampoPartenza;
    }


    /**
     * Ritorna il nome chiave del campo di arrivo.
     * <p/>
     *
     * @return il nome chiave del campo di arrivo.
     */
    public String getChiaveCampoArrivo() {
        return this.chiaveCampoArrivo;
    }


    /**
     * Regola il nome chiave del campo di arrivo.
     * <p/>
     *
     * @param chiaveCampoArrivo il nome chiave del campo di arrivo
     */
    private void setChiaveCampoArrivo(String chiaveCampoArrivo) {
        this.chiaveCampoArrivo = chiaveCampoArrivo;
    }


    public Campo getCampoPartenza() {
        return campoPartenza;
    }


    private void setCampoPartenza(Campo campoPartenza) {
        this.campoPartenza = campoPartenza;
    }


    public Campo getCampoArrivo() {
        return campoArrivo;
    }


    private void setCampoArrivo(Campo campoArrivo) {
        this.campoArrivo = campoArrivo;
    }


}