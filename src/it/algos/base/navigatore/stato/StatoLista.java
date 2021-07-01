/**
 * Title:     StatoLista
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      5-apr-2004
 */
package it.algos.base.navigatore.stato;

import it.algos.base.azione.Azione;
import it.algos.base.errore.Errore;
import it.algos.base.navigatore.info.Info;
import it.algos.base.navigatore.info.InfoLista;
import it.algos.base.portale.Portale;

/**
 * Regola lo stato di una <code>Lista</code>.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Controlla lo stato in cui si trova una Lista</li>
 * <li> Determina le Azioni possibili</li>
 * <li> Regola l'abilitazione dei Menu e Bottoni</li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 5-apr-2004 ore 9.09.13
 */
public class StatoLista extends StatoPortale {

    /**
     * riferimento al pacchetto di informazioni sulla Lista
     */
    private InfoLista stato = null;


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public StatoLista() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param unPortale riferimento al Portale che crea questo oggetto
     */
    public StatoLista(Portale unPortale) {
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
            this.setStato((InfoLista)unoStato);
            this.disabilitaAzioni();
            this.abilitaAzioni();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Disabilita tutte le azioni nella Lista.
     */
    protected void disabilitaAzioni() {
        super.disabilita(Azione.NUOVO_RECORD);
        super.disabilita(Azione.AGGIUNGI_RECORD);
        super.disabilita(Azione.MODIFICA_RECORD);
        super.disabilita(Azione.DUPLICA_RECORD);
        super.disabilita(Azione.ELIMINA_RECORD);
        super.disabilita(Azione.RIMUOVI_RECORD);
        super.disabilita(Azione.CARICA_LISTA);
        super.disabilita(Azione.REGISTRA_LISTA);
        super.disabilita(Azione.CHIUDE_LISTA);
        super.disabilita(Azione.EXPORT);
        super.disabilita(Azione.STAMPA);

        super.disabilita(Azione.ANNULLA_OPERAZIONE);
        super.disabilita(Azione.TAGLIA_TESTO);
        super.disabilita(Azione.COPIA_TESTO);
        super.disabilita(Azione.INCOLLA_TESTO);
        super.disabilita(Azione.ELIMINA_TESTO);
        super.disabilita(Azione.SELEZIONA_TUTTO);
        super.disabilita(Azione.MOSTRA_APPUNTI);

        super.disabilita(Azione.RICERCA);
        super.disabilita(Azione.PROIETTA);

        super.disabilita(Azione.CARICA_TUTTI);
        super.disabilita(Azione.SOLO_SELEZIONATI);
        super.disabilita(Azione.NASCONDE_SELEZIONATI);

        super.disabilita(Azione.PREFERITO);

        super.disabilita(Azione.RIGA_SU);
        super.disabilita(Azione.RIGA_GIU);
    }


    /**
     * Abilita solo le Azioni congruenti con lo stato attuale.
     */
    protected void abilitaAzioni() {

        super.abilita(Azione.PROIETTA);     // per ora sempre abilitato
        super.abilita(Azione.AGGIUNGI_RECORD);     // per ora sempre abilitato

        if (stato.isPossoChiudereFinestra()) {
            super.abilita(Azione.CHIUDE_LISTA);
        }// fine del blocco if

        if (stato.isPossoCaricareTutti()) {
            super.abilita(Azione.CARICA_TUTTI);
        }// fine del blocco if

        if (stato.isPossoCreareNuoviRecord()) {
            super.abilita(Azione.NUOVO_RECORD);
        }// fine del blocco if

        if (stato.isListaVuota()) {  // la lista e' vuota
            super.abilita(Azione.CARICA_LISTA);
            super.abilita(Azione.RICERCA);
        } else {    // la lista non e' vuota

            if (!stato.isRigheSelezionate()) {   // non ci sono righe selezionate
                super.abilita(Azione.CARICA_LISTA);
                super.abilita(Azione.REGISTRA_LISTA);
                super.abilita(Azione.EXPORT);
                super.abilita(Azione.STAMPA);
                super.abilita(Azione.RICERCA);
                super.abilita(Azione.CARICA_TUTTI);

            } else {    // ci sono righe selezionate

                if (!stato.isPiuRigheSelezionate()) {   // una sola riga e' selezionata

                    if (stato.isPossoModificareRecord()) {
                        super.abilita(Azione.MODIFICA_RECORD);
                    } /* fine del blocco if */

                    if (stato.isPossoDuplicareRecord()) {
                        super.abilita(Azione.DUPLICA_RECORD);
                    } /* fine del blocco if */

                    super.abilita(Azione.CARICA_LISTA);
                    super.abilita(Azione.REGISTRA_LISTA);
                    super.abilita(Azione.EXPORT);
                    super.abilita(Azione.STAMPA);
                    super.abilita(Azione.RICERCA);

                    super.abilita(Azione.CARICA_TUTTI);
                    super.abilita(Azione.SOLO_SELEZIONATI);
                    super.abilita(Azione.NASCONDE_SELEZIONATI);
                    super.abilita(Azione.PREFERITO);

                    if (stato.isPossoSpostareRigaListaGiu()) {
                        super.abilita(Azione.RIGA_GIU);
                    }// fine del blocco if
                    if (stato.isPossoSpostareRigaListaSu()) {
                        super.abilita(Azione.RIGA_SU);
                    }// fine del blocco if

                } else {    // piu' righe sono selezionate

                    if (!stato.isTutteRigheSelezionate()) { // non sono selezionate tutte le righe
                        super.abilita(Azione.CARICA_LISTA);
                        super.abilita(Azione.REGISTRA_LISTA);
                        super.abilita(Azione.EXPORT);
                        super.abilita(Azione.STAMPA);
                        super.abilita(Azione.RICERCA);
                        super.abilita(Azione.CARICA_TUTTI);
                        super.abilita(Azione.SOLO_SELEZIONATI);
                        super.abilita(Azione.NASCONDE_SELEZIONATI);
                    } else {            // sono selezionate tutte le righe
                        super.abilita(Azione.CARICA_LISTA);
                        super.abilita(Azione.REGISTRA_LISTA);
                        super.abilita(Azione.EXPORT);
                        super.abilita(Azione.STAMPA);
                        super.abilita(Azione.RICERCA);
                        super.abilita(Azione.CARICA_TUTTI);
                        super.abilita(Azione.SOLO_SELEZIONATI);
                        super.abilita(Azione.NASCONDE_SELEZIONATI);
                    } /* fine del blocco if/else */

                } /* fine del blocco if-else */


                if (stato.isPossoCancellareRecord()) {
                    super.abilita(Azione.ELIMINA_RECORD);
                    super.abilita(Azione.RIMUOVI_RECORD);
                } /* fine del blocco if-else */

            } /* fine del blocco if-else */

        } /* fine del blocco if-else */

        /* Abilita ModificaRecord per l'estratto */
        if (stato.isPossoModificareRecord()) {
            if (stato.isEstratto()) {
                if (stato.getQuanteRighe() == 1) {
                    super.abilita(Azione.MODIFICA_RECORD);
                }// fine del blocco if
            } /* fine del blocco if-else */
        }// fine del blocco if

        /* Abilita EliminaRecord per l'estratto */
        if (stato.isPossoCancellareRecord()) {
            if (stato.isEstratto()) {
                if (stato.getQuanteRighe() == 1) {
                    super.abilita(Azione.ELIMINA_RECORD);
                }// fine del blocco if
            } /* fine del blocco if-else */
        }// fine del blocco if
    }


    private void setStato(InfoLista stato) {
        this.stato = stato;
    }

}// fine della classe
