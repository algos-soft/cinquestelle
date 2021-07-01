/**
 * Title:     StatoNavigatore
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      5-apr-2004
 */
package it.algos.base.navigatore.stato;

import it.algos.base.azione.Azione;
import it.algos.base.errore.Errore;
import it.algos.base.navigatore.info.Info;
import it.algos.base.portale.Portale;

import java.util.LinkedHashMap;

/**
 * Superclasse astratta per le classi di <i>stato</i> del <code>Portale</code>.
 * </p>
 * Questa classe astratta: <ul>
 * <li> Serve come superclasse di <code>StatoLista</code> e <code>StatoScheda</code></li>
 * <li> Mantiene alcuni attributi comuni alle sottoclassi</li>
 * <li> mantiene alcuni metodi</li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 5-apr-2004 ore 10.30.07
 */
public abstract class StatoPortale {

    /**
     * riferimento al Portale che crea questo oggetto
     */
    protected Portale portale = null;

    /**
     * riferimento alle azioni su cui agire
     */
    private LinkedHashMap<String, Azione> azioni = null;


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public StatoPortale() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param unPortale riferimento al Portale che crea questo oggetto
     */
    public StatoPortale(Portale unPortale) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setPortale(unPortale);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        this.setAzioni(this.getPortale().getAzioni());
    }// fine del metodo inizia


    /**
     * Regola la GUI del Portale.
     * <p/>
     * Sovrascritto nelle sottoclassi.
     */
    public void regola(Info info) {
    }


    /**
     * Abilita la singola Azione.
     * </p>
     * Controlla che esistano le azioni di riferimento <br>
     * Controlla che la singola azione esista <br>
     * Controlla che l'azione sia attiva. Esiste un flag su ogni azione per
     * disattivarla (in fase di sviluppo o per qualche specifico utente) <br>
     * Esegue <bold>solo</bold> se l'azione era disabilitata. Per evitare
     * lavoro (ed eventuale sfarfallio) inutile alla GUI <br>
     *
     * @param unaChiaveAzione nome identificativo dell'Azione nella collezione
     */
    protected void abilita(String unaChiaveAzione) {
        /* variabili e costanti locali di lavoro */
        Azione unAzione = null;

        try {    // prova ad eseguire il codice
            /* controlla che la collezione di azioni esista */
            if (azioni != null) {
                unAzione = (Azione)azioni.get(unaChiaveAzione);

                /* controlla che l'azione esista */
                if (unAzione != null) {
                    /* controlla che l'azione sia attiva */
                    if (unAzione.isAttiva()) {
                        /* esegue solo se necessario */
                        if (unAzione.getAzione().isEnabled() == false) {
                            unAzione.getAzione().setEnabled(true);
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Disabilita la singola Azione.
     * </p>
     * Controlla che esistano le azioni di riferimento <br>
     * Controlla che la singola azione esista <br>
     * Controlla che l'azione sia attiva. Esiste un flag su ogni azione per
     * disattivarla (in fase di sviluppo o per qualche specifico utente) <br>
     * Esegue <bold>solo</bold> se l'azione era abilitata. Per evitare
     * lavoro (ed eventuale sfarfallio) inutile alla GUI <br>
     *
     * @param unaChiaveAzione nome identificativo dell'Azione nella collezione
     */
    protected void disabilita(String unaChiaveAzione) {
        /* variabili e costanti locali di lavoro */
        Azione unAzione = null;

        try {    // prova ad eseguire il codice
            /* controlla che la collezione di azioni esista */
            if (azioni != null) {
                unAzione = (Azione)azioni.get(unaChiaveAzione);

                /* controlla che l'azione esista */
                if (unAzione != null) {
                    /* controlla che l'azione sia attiva */
                    if (unAzione.isAttiva()) {
                        /* esegue solo se necessario */
                        if (unAzione.getAzione().isEnabled()) {
                            unAzione.getAzione().setEnabled(false);
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    private void setPortale(Portale portale) {
        this.portale = portale;
    }


    protected Portale getPortale() {
        return portale;
    }


    public LinkedHashMap<String, Azione> getAzioni() {
        return azioni;
    }


    public void setAzioni(LinkedHashMap<String, Azione> azioni) {
        this.azioni = azioni;
    }
}// fine della classe
