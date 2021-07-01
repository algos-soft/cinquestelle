/**
 * Title:        QueryPassaggiSelezione.java
 * Package:      it.algos.base.query
 * Description:  Modello di Passaggi Obbligati per una QuerySelezione
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 28 luglio 2003 alle 15.00
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002, 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.query.selezione;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;

import java.util.ArrayList;


/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Definire la struttura interna dei Passaggi Obbligati per la QuerySelezione <br>
 * Un oggetto Passaggi per la QuerySelezione e' composto da un elenco ordinato di <br>
 * oggetti di tipo QuerySelezionePassaggioObbligato. <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  28 luglio 2003 alle 15.00
 */
public final class PassaggiSelezione extends Object {


    /**
     * elenco degli oggetti QuerySelezionePassaggioObbligato per la QuerySelezione
     */
    private ArrayList unElencoPassaggi = null;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public PassaggiSelezione() {
        try { // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo costruttore base */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /** crea inizialmente gli array */
        this.unElencoPassaggi = new ArrayList();
    } /* fine del metodo inizia */


    /**
     * aggiunge un Passaggio all'elenco dei Passaggi
     *
     * @param un oggetto di tipo QuerySelezionePassaggioObbligato
     */
    public void addPassaggio(PassaggioObbligato unPassaggio) {
        unElencoPassaggi.add(unPassaggio);
    } /* fine del metodo */


    /**
     * aggiunge un Passaggio all'elenco dei Passaggi
     *
     * @param una Tavola di destinazione
     * @param un Campo dal quale passare obbligatoriamente
     */
    public void addPassaggio(String unaTavolaDestinazione, Campo unCampoPassaggio) {
        PassaggioObbligato unPassaggioObbligato = new PassaggioObbligato(unaTavolaDestinazione,
                unCampoPassaggio);
        this.addPassaggio(unPassaggioObbligato);
    } /* fine del metodo */


    /**
     * Aggiunge un Passaggio all'elenco dei Passaggi.
     * <p/>
     *
     * @param modulo il modulo di destinazione
     * @param campo il Campo dal quale passare obbligatoriamente
     */
    public void addPassaggio(Modulo modulo, Campo campo) {
        String tavola = modulo.getModello().getTavolaArchivio();
        this.addPassaggio(tavola, campo);
    } /* fine del metodo */


    /**
     * Pulisce l'elenco dei Passaggi
     */
    public void reset() {
        unElencoPassaggi.clear();
    } /* fine del metodo */


    /**
     * regolazione dell'elenco dei Passaggi
     *
     * @param un elenco di oggetti QuerySelezionePassaggioObbligato
     */
    public void setPassaggi(ArrayList unElencoPassaggi) {
        this.unElencoPassaggi = unElencoPassaggi;
    } /* fine del metodo setter */


    /**
     * restituisce l'elenco dei Passaggi
     *
     * @return un elenco di oggetti QuerySelezionePassaggioObbligato
     */
    public ArrayList getPassaggi() {
        return this.unElencoPassaggi;
    } /* fine del metodo getter */


    /**
     * restituisce un singolo oggetto Passaggi
     *
     * @param l'indice dell'oggetto Passaggio da restituire
     *
     * @return un oggetto QuerySelezionePassaggioObbligato
     */
    public PassaggioObbligato getPassaggio(int indicePassaggio) {
        /** variabili e costanti locali di lavoro */
        ArrayList unElencoPassaggi = null;
        PassaggioObbligato unPassaggio = null;

        /** recupero l'elenco degli oggetti Passaggio */
        unElencoPassaggi = this.getPassaggi();

        /** Controlla se il numero di elemento richiesto esiste */
        if (unElencoPassaggi.size() > indicePassaggio) {
            unPassaggio = (PassaggioObbligato)unElencoPassaggi.get(indicePassaggio);
        } /* fine del blocco if */

        /** valore di ritorno */
        return unPassaggio;
    } /* fine del metodo getter */


    /**
     * ritorna il numero di passaggi contenuti nell'oggetto
     */
    public int getQuantiPassaggi() {
        return this.getPassaggi().size();
    }// fine del metodo getter


    /**
     * Restituisce l'elenco dei passaggi obbligati per una
     * data tavola destinazione.
     * <p/>
     *
     * @param tavola la tavola destinazione
     *
     * @return l'elenco delle relazioni obbligate
     */
    public ArrayList getPassaggiTavola(String tavola) {

        /** variabili e costanti locali di lavoro */
        ArrayList passaggiTavola = null;
        ArrayList passaggi = new ArrayList();
        PassaggioObbligato passaggio = null;
        String unaTavola = "";

        try {                                   // prova ad eseguire il codice
            passaggiTavola = new ArrayList();

            /*
             * spazzola tutti i passaggi obbligati ed estrae quelli
             * che riguardano la tavola specificata
             */
            passaggi = this.getPassaggi();
            for (int k = 0; k < passaggi.size(); k++) {

                // estrae l'oggetto Passaggio Obbligato
                passaggio = (PassaggioObbligato)passaggi.get(k);

                // estrae il nome della tavola destinazione
                unaTavola = passaggio.getTavolaDestinazione();

                // confronta le tavole; se sono uguali aggiunge il passaggio
                if (tavola.equals(unaTavola)) {
                    passaggiTavola.add(passaggio);
                } /* fine del blocco if */

            } /* fine del blocco for */

        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        return passaggiTavola;
    } /* fine del metodo */

    //-------------------------------------------------------------------------
}// fine della classe it.algos.base.query.QueryCampiSelezione.java

//-----------------------------------------------------------------------------

