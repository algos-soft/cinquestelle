/**
 * Title:     ${NAME}
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      28-gen-2005
 */
package it.algos.base.campo.inizializzatore;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;

/**
 * Inizializzatore di default.
 * <p/>
 * Ritorna il valore vuoto di Business Logic per il campo.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 28-gen-2005 ore 10.09.49
 */
final class InitDefault extends InitBase {

    /**
     * Campo di riferimento
     */
    private Campo campo = null;


    /**
     * Costruttore completo.
     * <p/>
     *
     * @param campo il campo di riferimento da inizializzare
     */
    public InitDefault(Campo campo) {
        /* rimanda al costruttore della superclasse */
        super();

        this.setCampo(campo);

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
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


    /**
     * Ritorna il valore di inizializzazione per il campo.
     * <p/>
     * Il valore ritornato e' a livello di Memoria
     */
    public Object getValore() {

        /* variabili e costanti locali di lavoro */
        Object unValore = null;
        Campo campo = null;

        try { // prova ad eseguire il codice
            campo = this.getCampo();
            unValore = campo.getCampoDati().getValoreMemoriaVuoto();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        return unValore;
    } /* fine del metodo */


    private Campo getCampo() {
        return campo;
    }


    private void setCampo(Campo campo) {
        this.campo = campo;
    }

}// fine della classe
