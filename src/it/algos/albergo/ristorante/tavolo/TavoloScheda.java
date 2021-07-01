/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      17-gen-2005
 */
package it.algos.albergo.ristorante.tavolo;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.scheda.SchedaBase;

/**
 * Presentazione grafica di un singolo record di Tavolo.
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
 * @version 1.0    / 17-gen-2005 ore 8.22.03
 */
public final class TavoloScheda extends SchedaBase implements Tavolo {

    /**
     * Costruttore completo senza parametri.
     */
    public TavoloScheda(Modulo modulo) {
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
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     */
    @Override public void inizializza() {
        super.inizializza();
    }


    /**
     * Crea le pagine.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    protected void creaPagine() {
        /* variabili e costanti locali di lavoro */
        Pagina pagina;

        try {    // prova ad eseguire il codice
            /* crea una pagina vuota col titolo */
            pagina = super.addPagina("tavolo");

            pagina.add(Cam.numtavolo);
            pagina.add(Cam.sala);
            pagina.add(Cam.occupato);
            pagina.add(Cam.numcoperti);
            pagina.add(Cam.nomecliente);
            pagina.add(Cam.mezzapensione);
            pagina.add(Cam.pasto);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Metodo eseguito quando il valore memoria di un campo cambia.
     * <p/>
     * Regola la visibilita' dei campi nella scheda <br>
     */
    public void sincronizza() {
        super.sincronizza();
        try { // prova ad eseguire il codice
            this.regolaAbilitaDaOccupato();
            this.regolaAbilitaDaMezzaPensione();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola la modificabilità di alcuni campi
     * in funzione del flag Occupato.<br>
     */
    private void regolaAbilitaDaOccupato() {
        /* variabili e costanti locali di lavoro */
        Campo campoOccupato;
        Campo campoCoperti;
        Campo campoCliente;
        Campo campoMezzaPensione;
        boolean statoFlag;
        boolean abilitato = false;

        try {    // prova ad eseguire il codice

            /* recupera i campi Mezza Pensione e Pasto */
            campoOccupato = this.getCampo(Tavolo.Cam.occupato);
            campoCoperti = this.getCampo(Tavolo.Cam.numcoperti);
            campoCliente = this.getCampo(Tavolo.Cam.nomecliente);
            campoMezzaPensione = this.getCampo(Tavolo.Cam.mezzapensione);

            /* recupera lo stato del flag Occupato */
            statoFlag = (Boolean)campoOccupato.getCampoDati().getMemoria();

            /* regola il flag abilitato */
            if (statoFlag) {
                abilitato = true;
            }// fine del blocco if

            /* regola la abilitazione dei campi */
            campoCoperti.setModificabile(abilitato);
            campoCliente.setModificabile(abilitato);
            campoMezzaPensione.setModificabile(abilitato);


        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    } // fine del metodo


    /**
     * Regola la abilitazione di alcuni campi
     * in funzione del flag MezzaPensione.<br>
     */
    private void regolaAbilitaDaMezzaPensione() {
        /* variabili e costanti locali di lavoro */
        Campo campoMezzaPensione;
        Campo campoPasto;
        boolean statoFlag;
        boolean abilitato = false;

        try {    // prova ad eseguire il codice
            campoMezzaPensione = this.getCampo(Tavolo.Cam.mezzapensione);
            campoPasto = this.getCampo(Tavolo.Cam.pasto);

            /* recupera lo stato del flag MezzaPensione */
            statoFlag = (Boolean)campoMezzaPensione.getCampoDati().getMemoria();

            /* regola il flag abilitato */
            if (statoFlag) {
                if (campoMezzaPensione.isAbilitato()) {
                    abilitato = true;
                }// fine del blocco if
            }// fine del blocco if

            /* regola la abilitazione del campo Pasto */
            if (campoPasto != null) {
                campoPasto.setModificabile(abilitato);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    } // fine del metodo


    /**
     * Controlla se la scheda e' valida.
     * </p>
     * Se è a mezza pensione ci vuole il campo pasto
     *
     * @return true se i campi sono tutti validi <br>
     */
    public boolean isValida() {
        /* variabili e costanti locali di lavoro */
        boolean valida = false;
        Object valore;
        boolean mezza;
        Campo campoPasto;

        /* valore di ritorno */
        try { // prova ad eseguire il codice
            valida = super.isValida();
            if (valida) {
                valore = this.getValore(Tavolo.Cam.mezzapensione.get());
                mezza = Libreria.getBool(valore);
                if (mezza) {
                    campoPasto = this.getCampo(Tavolo.Cam.pasto.get());
                    if (campoPasto.isVuoto()) {
                        valida = false;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valida;
    }

}// fine della classe
