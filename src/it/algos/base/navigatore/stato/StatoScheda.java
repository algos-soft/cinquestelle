/**
 * Title:     StatoScheda
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      9-nov-2004
 */
package it.algos.base.navigatore.stato;

import it.algos.base.azione.Azione;
import it.algos.base.errore.Errore;
import it.algos.base.navigatore.info.Info;
import it.algos.base.navigatore.info.InfoScheda;
import it.algos.base.portale.Portale;

/**
 * Regola lo stato di una <code>Scheda</code>.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Controlla lo stato in cui si trova una Scheda</li>
 * <li> Determina le Azioni possibili</li>
 * <li> Regola l'abilitazione dei Menu e Bottoni</li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 9-nov-2004 ore 9.09.13
 */
public class StatoScheda extends StatoPortale {

    /**
     * riferimento al pacchetto di informazioni sulla Lista
     */
    private InfoScheda stato = null;


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public StatoScheda() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param unPortale riferimento al Portale che crea questo oggetto
     */
    public StatoScheda(Portale unPortale) {
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
            this.setStato((InfoScheda)unoStato);
            this.disabilitaAzioni();
            this.abilitaAzioni();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Disabilita tutte le azioni nella Scheda.
     */
    protected void disabilitaAzioni() {
        super.disabilita(Azione.CHIUDE_SCHEDA);
        super.disabilita(Azione.REGISTRA_SCHEDA);
        super.disabilita(Azione.ANNULLA_MODIFICHE);
        super.disabilita(Azione.STAMPA);

        super.disabilita(Azione.PRIMO_RECORD);
        super.disabilita(Azione.RECORD_PRECEDENTE);
        super.disabilita(Azione.RECORD_SUCCESSIVO);
        super.disabilita(Azione.ULTIMO_RECORD);
    }


    /**
     * Abilita solo le Azioni congruenti con lo stato attuale.
     */
    protected void abilitaAzioni() {
        /* variabili e costanti locali di lavoro */
        InfoScheda stato = null;

        try { // prova ad eseguire il codice

            stato = this.getStato();

            if (stato != null) {
                if (stato.isAbilitaChiudi()) {
                    super.abilita(Azione.CHIUDE_SCHEDA);
                }// fine del blocco if

                if (stato.isPossoRipristinare()) {
                    super.abilita(Azione.ANNULLA_MODIFICHE);
                }// fine del blocco if

                if (stato.isPossoRegistrare()) {
                    super.abilita(Azione.REGISTRA_SCHEDA);
                }// fine del blocco if

                if (stato.isAbilitaPrimo()) {
                    super.abilita(Azione.PRIMO_RECORD);
                }// fine del blocco if

                if (stato.isAbilitaPrecedente()) {
                    super.abilita(Azione.RECORD_PRECEDENTE);
                }// fine del blocco if

                if (stato.isAbilitaSuccessivo()) {
                    super.abilita(Azione.RECORD_SUCCESSIVO);
                }// fine del blocco if

                if (stato.isAbilitaUltimo()) {
                    super.abilita(Azione.ULTIMO_RECORD);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    private InfoScheda getStato() {
        return stato;
    }


    private void setStato(InfoScheda stato) {
        this.stato = stato;
    }

}// fine della classe
