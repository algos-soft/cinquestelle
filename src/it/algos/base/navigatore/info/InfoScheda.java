/**
 * Title:     InfoScheda
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      9-nov-2004
 */
package it.algos.base.navigatore.info;

import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.navigatore.LogicaNavigatore;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.portale.Portale;
import it.algos.base.scheda.Scheda;

/**
 * Wrapper per le informazioni sullo stato di una Scheda.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Costruisce un tipo di dati </li>
 * <li> Raggruppa questi dati in un wrapper </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 9-nov-2004 ore 15.52.26
 */
public final class InfoScheda extends Object implements Info {

    /**
     * Scheda di riferimento
     */
    private Scheda scheda = null;

    /**
     * flag per indicare quando si puo' ricaricare la scheda
     */
    private boolean isPossoRipristinare = false;

    /**
     * flag per indicare quando si puo' registrare la scheda
     */
    private boolean isPossoRegistrare = false;

    /**
     * flag - abilitazionme del bottone vai al primo record
     */
    private boolean isAbilitaPrimo = false;

    /**
     * flag - abilitazionme del bottone vai al record precedente
     */
    private boolean isAbilitaPrecedente = false;

    /**
     * flag - abilitazionme del bottone vai al record successivo
     */
    private boolean isAbilitaSuccessivo = false;

    /**
     * flag - abilitazione del bottone vai all'ultimo record
     */
    private boolean isAbilitaUltimo = false;

    /**
     * flag - abilitazione del bottone chiudi scheda
     */
    private boolean isAbilitaChiudi = false;


    /**
     * Costruttore completo. <br>
     *
     * @param scheda la scheda di riferimento
     */
    public InfoScheda(Scheda scheda) {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice

            /* regola le variabili di istanza coi parametri */
            this.setScheda(scheda);

            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     * Disabilita tutte le azioni <br>
     * Determina le Azioni possibili <br>
     * Abilita solo le Azioni congruenti con lo stato attuale <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     *
     * @return vero se viene inizializzato adesso;
     *         falso se era gi&agrave; stato inizializzato
     */
    public boolean inizializza() {

        /* valore di ritorno */
        return true;
    }


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Regola le variabili del pacchetto in funzione dello stato corrente <br>
     */
    public void avvia() {
        /* variabili e costanti locali di lavoro */
        boolean abilita;
        Navigatore nav;

        try { // prova ad eseguire il codice

            /* pulisce tutti i flags */
            isAbilitaPrimo = false;
            isAbilitaPrecedente = false;
            isAbilitaSuccessivo = false;
            isAbilitaUltimo = false;
            isPossoRipristinare = false;
            isPossoRegistrare = false;
            isAbilitaChiudi = false;

            /* ==== abilitazione ripristino dei valori dal database ==== */
            abilita = true;

            /* il codice deve essere > 0*/
            if (abilita) {
                if (this.getScheda().getCodice() <= 0) {
                    abilita = false;
                }// fine del blocco if
            }// fine del blocco if

            /* la scheda deve essere stata modificata */
            if (abilita) {
                if (!this.getScheda().isModificata()) {
                    abilita = false;
                }// fine del blocco if-else
            }// fine del blocco if

            /* se la scheda è contenuta in un Navigatore, questo deve essere modificabile */
            if (abilita) {
                nav = this.getNavigatore();
                if (nav != null) {
                    if (!nav.isModificabile()) {
                        abilita = false;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            isPossoRipristinare = abilita;

            /* ==== abilitazione registrazione del record ==== */
            abilita = true;

            /* il codice deve essere > 0*/
            if (abilita) {
                if (this.getScheda().getCodice() <= 0) {
                    abilita = false;
                }// fine del blocco if
            }// fine del blocco if

            /* se la scheda è contenuta in un Navigatore, questo deve essere modificabile */
            if (abilita) {
                nav = this.getNavigatore();
                if (nav != null) {
                    if (!nav.isModificabile()) {
                        abilita = false;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            isPossoRegistrare = abilita;     //la scheda controllerà poi se è veramente possibile

//            if (this.getNavigatore().isModificabile()) {
//                if (this.getScheda().getCodice() != 0) {
//                    //la scheda controllerà poi se è veramente possibile
//                    isPossoRegistrare = true;
//                }// fine del blocco if
//            }// fine del blocco if

            /* abilitazione frecce spostamento */
            if (!this.getScheda().isNuovoRecord()) {
                setAbilitaPrimo(this.isSpostabile(Dati.Spostamento.primo));
                setAbilitaPrecedente(this.isSpostabile(Dati.Spostamento.precedente));
                setAbilitaSuccessivo(this.isSpostabile(Dati.Spostamento.successivo));
                setAbilitaUltimo(this.isSpostabile(Dati.Spostamento.ultimo));
            }// fine del blocco if

            /* abilitazione chiudi scheda */
            if (this.getScheda().getCodice() > 0) {
                isAbilitaChiudi = true;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Controlla nel navigatore la possibilità di spostamento di record in scheda.
     *
     * @param spostamento tipologia del bottone di spostamento esaminato
     */
    private boolean isSpostabile(Dati.Spostamento spostamento) {
        /* variabili e costanti locali di lavoro */
        boolean spostabile = false;
        Navigatore nav;
        LogicaNavigatore logica;
        int codice;

        try { // prova ad eseguire il codice
            codice = this.getCodice();
            nav = this.getNavigatore();
            if (nav != null) {
                logica = nav.getLogica();
                spostabile = logica.isSchedaSpostabile(codice, spostamento);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return spostabile;
    }


    /**
     * Recupera il navigatore.
     * <p/>
     */
    private Navigatore getNavigatore() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav = null;
        Portale portale;

        try { // prova ad eseguire il codice
            portale = this.getPortale();
            if (portale != null) {
                nav = portale.getNavigatore();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }


    /**
     * Recupera il portale.
     * <p/>
     */
    private Portale getPortale() {
        /* valore di ritorno */
        return this.getScheda().getPortale();
    }


    /**
     * Recupera il codice della scheda.
     * <p/>
     *
     * @return il codice della scheda
     */
    private int getCodice() {
        /* valore di ritorno */
        return this.getScheda().getCodice();
    }


    private Scheda getScheda() {
        return scheda;
    }


    private void setScheda(Scheda scheda) {
        this.scheda = scheda;
    }


    public boolean isPossoRipristinare() {
        return isPossoRipristinare;
    }


    public boolean isPossoRegistrare() {
        return isPossoRegistrare;
    }


    public boolean isAbilitaPrimo() {
        return isAbilitaPrimo;
    }


    private void setAbilitaPrimo(boolean abilitaPrimo) {
        isAbilitaPrimo = abilitaPrimo;
    }


    public boolean isAbilitaPrecedente() {
        return isAbilitaPrecedente;
    }


    private void setAbilitaPrecedente(boolean abilitaPrecedente) {
        isAbilitaPrecedente = abilitaPrecedente;
    }


    public boolean isAbilitaSuccessivo() {
        return isAbilitaSuccessivo;
    }


    private void setAbilitaSuccessivo(boolean abilitaSuccessivo) {
        isAbilitaSuccessivo = abilitaSuccessivo;
    }


    public boolean isAbilitaUltimo() {
        return isAbilitaUltimo;
    }


    private void setAbilitaUltimo(boolean abilitaUltimo) {
        isAbilitaUltimo = abilitaUltimo;
    }


    public boolean isAbilitaChiudi() {
        return isAbilitaChiudi;
    }

}// fine della classe
