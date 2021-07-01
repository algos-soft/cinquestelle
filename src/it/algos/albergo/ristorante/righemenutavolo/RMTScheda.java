/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      29-mar-2005
 */
package it.algos.albergo.ristorante.righemenutavolo;

import it.algos.albergo.ristorante.menu.MenuModulo;
import it.algos.albergo.ristorante.tavolo.Tavolo;
import it.algos.albergo.ristorante.tavolo.TavoloModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Libreria;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.scheda.SchedaBase;

/**
 * Presentazione grafica di un singolo record di RMT.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Crea le pagine del <code>Libro</code> che vengono visualizzate nel
 * PortaleScheda del Navigatore </li>
 * <li> Ogni pagina viene creata con un set di campi o aggiungendo i singoli campi </li>
 * <li> I campi vengono posizionati in automatico oppure singolarmente </li>
 * <li> Se uno stesso campo viene posizionato su pi&ugrave; pagine, risulter&agrave;
 * visibile solo nell'ultima pagina in cui viene posizionato </li>
 * <li> Se il <code>Modello>/code> prevede il campo note, crea la pagina note </li>
 * <li> Se il flag programma &egrave; attivo, crea la pagina programmatore </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 29-mar-2005 ore 17.54.09
 */
public final class RMTScheda extends SchedaBase implements RMT {

    /**
     * Costruttore completo.
     * <p/>
     *
     * @param modulo di riferimento
     */
    public RMTScheda(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


    /**
     * Crea le pagine.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.scheda.SchedaBase#addPagina
     */
    protected void creaPagine() {
        /* variabili e costanti locali di lavoro */
        Pagina pagina = null;
        Pannello pan = null;

        /* crea una pagina vuota col titolo */
        pagina = super.addPagina("RMT");

        try {    // prova ad eseguire il codice

            pan = new PannelloFlusso(this, Layout.ORIENTAMENTO_ORIZZONTALE);
            pan.add(RMT.Cam.tavolo.get());
            pan.add(RMT.Cam.camera.get());
            pan.add(RMT.Cam.coperti.get());
            pan.add(RMT.Cam.comandato.get());
            pagina.add(pan);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Verifica se la scheda e' teoricamente registrabile.
     * <p/>
     *
     * @return true se e' registrabile
     */
    public boolean isRegistrabile() {
        /* variabili e costanti locali di lavoro */
        boolean registrabile = false;
        Campo campo = null;
        Object valore = null;
        int codiceTavolo = 0;
        int codiceRiga = 0;
        int numeroTavolo = 0;

        try { // prova ad eseguire il codice
            registrabile = super.isRegistrabile();
            if (registrabile) {
                campo = this.getCampo(RMT.Cam.tavolo.get());
                campo.getCampoDati().videoMemoria();
                valore = campo.getCampoDati().getMemoria();
                codiceTavolo = Libreria.objToInt(valore);
                codiceRiga = this.getChiaveRigaTavolo(codiceTavolo);
                if (codiceRiga != 0) {
                    if (codiceRiga != this.getCodice()) {
                        numeroTavolo = this.getNumeroTavolo(codiceTavolo);
                        new MessaggioAvviso("Tavolo " + numeroTavolo + " gia' presente nel menu.");
                        registrabile = false;
                    }// fine del blocco if

                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return registrabile;

    }


    /**
     * Ritorna la chiave di riga per un dato tavolo nel menu corrente.
     * <p/>
     *
     * @param chiaveTavolo il codice chiave del tavolo da cercare
     *
     * @return la chiave dela riga, 0 se non trovato.
     */
    private int getChiaveRigaTavolo(int chiaveTavolo) {
        /* variabili e costanti locali di lavoro */
        int chiaveRiga = 0;
        Modulo moduloMenu;
        Modulo moduloTavolo;
        Campo campoMenuCodice = null;
        Campo campoTavoloChiave = null;
        Filtro filtro = null;
        Filtro f = null;
        int[] valoriChiave = null;
        int codiceMenu = 0;

        try {    // prova ad eseguire il codice
            moduloMenu = MenuModulo.get();
            moduloTavolo = TavoloModulo.get();
            campoMenuCodice = moduloMenu.getCampoChiave();
            campoTavoloChiave = moduloTavolo.getCampoChiave();
            codiceMenu = this.getCodiceMenuCorrente();
            if (codiceMenu != 0) {
                filtro = new Filtro();
                f = FiltroFactory.crea(campoMenuCodice, this.getCodiceMenuCorrente());
                filtro.add(f);
                f = FiltroFactory.crea(campoTavoloChiave, chiaveTavolo);
                filtro.add(f);
                valoriChiave = this.getModulo().query().valoriChiave(filtro);
                if (valoriChiave.length > 0) {
                    chiaveRiga = valoriChiave[0];
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return chiaveRiga;
    }


    /**
     * Ritorna il numero di un tavolo data la chiave.
     * <p/>
     *
     * @param chiaveTavolo la chiave del tavolo
     *
     * @return il numero del tavolo
     */
    private int getNumeroTavolo(int chiaveTavolo) {
        /* variabili e costanti locali di lavoro */
        int numeroTavolo = 0;
        Modulo moduloTavolo = TavoloModulo.get();
        Campo campoTavoloNumero = moduloTavolo.getCampo(Tavolo.Cam.numtavolo);
        Object valore = null;

        try {    // prova ad eseguire il codice
            valore = moduloTavolo.query().valoreCampo(campoTavoloNumero, chiaveTavolo);
            numeroTavolo = Libreria.objToInt(valore);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return numeroTavolo;
    }


    /**
     * Ritorna il codice del menu corrente.
     * <p/>
     *
     * @return il codice del menu che pilota il navigatore
     */
    private int getCodiceMenuCorrente() {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        Navigatore nav;

        try { // prova ad eseguire il codice
            nav = this.getNavigatore();
            if (nav != null) {
                codice = nav.getValorePilota();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


}// fine della classe
