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
import it.algos.base.navigatore.info.InfoNavigatore;
import it.algos.base.portale.Portale;

/**
 * Regola lo stato di un <code>Navigatore</code>.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Controlla lo stato in cui si trova un Navigatore</li>
 * <li> Determina le Azioni possibili</li>
 * <li> Regola l'abilitazione dei Menu e Bottoni</li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 5-apr-2004 ore 9.09.13
 */
public class StatoNavigatore extends StatoPortale {

    /**
     * riferimento al pacchetto di informazioni sul Navigatore
     */
    private InfoNavigatore stato = null;


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public StatoNavigatore() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param unPortale riferimento al Portale che crea questo oggetto
     */
    public StatoNavigatore(Portale unPortale) {
        /* rimanda al costruttore della superclasse */
        super(unPortale);

        try { // prova ad eseguire il codice
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
     * Regola la GUI del Portale.
     * <p/>
     */
    public void regola(Info unoStato) {

        try {    // prova ad eseguire il codice
            this.setStato((InfoNavigatore)unoStato);
            this.disabilitaAzioni();
            this.abilitaAzioni();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Disabilita tutte le azioni nel Navigatore.
     */
    protected void disabilitaAzioni() {
        super.disabilita(Azione.CHIUDE_NAVIGATORE);
//        super.disabilita(Azione.ESCE_PROGRAMMA);
    }


    /**
     * Abilita solo le Azioni congruenti con lo stato attuale.
     */
    protected void abilitaAzioni() {
        if (stato.isPossoChiudereFinestra()) {
            super.abilita(Azione.CHIUDE_NAVIGATORE);
        }// fine del blocco if

        if (true) {
//            super.abilita(Azione.ESCE_PROGRAMMA);
        }// fine del blocco if

    }


    private void setStato(InfoNavigatore stato) {
        this.stato = stato;
    }

}// fine della classe
