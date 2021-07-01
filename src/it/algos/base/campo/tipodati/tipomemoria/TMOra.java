/**
 * Title:        TMTimestamp.java
 * Package:      it.algos.base.campo.tipomemoria
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /
 * Creato:       il 20 ottobre 2003 alle 12.11
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.campo.tipodati.tipomemoria;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;

import java.sql.Time;

/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Implementare il <i>design pattern</i> <b>Singleton</b>  <br>
 * B - Definire un modello dati per il tipo Memoria Timestamp <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  20 ottobre 2003 alle 12.11
 */
public final class TMOra extends TMBase implements TipoMemoria {


    /**
     * istanza unica di questa classe, creata ad avvio del programma
     */
    private final static TMOra ISTANZA = new TMOra();


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    private TMOra() {
        /* rimanda al costruttore della superclasse */
        super();

        /* regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        this.setClasse(Integer.class);
        super.setValoreVuoto(0);
    } /* fine del metodo inizia */


    /**
     * Controlla se un campo e' stato modificato
     * per questo tipo specifico di dati Memoria
     * Se si arriva qui, gli oggetti valore Memoria e Backup sono entrambi
     * non nulli, della stessa classe e della classe di questo tipo Memoria
     *
     * @param unCampo campo da controllare
     *
     * @return true se e' modificato
     */
    private boolean isModificatoSpecifico(Campo unCampo) {

        /** variabili e costanti locali di lavoro */
        boolean modificato = false;
        Object unOggettoMemoria;
        Object unOggettoBackup;
        Integer unValoreMemoria;
        Integer unValoreBackup;

        /** recupera il valore della memoria */
        unOggettoMemoria = unCampo.getCampoDati().getMemoria();

        /** recupera il valore del backup */
        unOggettoBackup = unCampo.getCampoDati().getBackup();

        /** controlla se sono diversi */
        unValoreMemoria = (Integer)unOggettoMemoria;
        unValoreBackup = (Integer)unOggettoBackup;
        if (unValoreMemoria != unValoreBackup) {
            modificato = true;
        } /* fine del blocco if */

        return modificato;
    } /* fine del metodo */


    /**
     * Controlla se un campo e' modificato
     * confronta il valore memoria col valore backup
     *
     * @param unCampo da controllare
     *
     * @return true se e' modificato
     */
    public boolean isCampoModificato(Campo unCampo) {

        /** variabili e costanti locali di lavoro */
        boolean modificato = false;
        int unCodice;

        /** controlla se e' modificato nella superclasse
         *  (controlla eventuali valori nulli)*/
        unCodice = super.isModificato(unCampo);

        /** selezione */
        switch (unCodice) {
            case NON_MODIFICATO:
                modificato = false;
                break;
            case MODIFICATO:
                modificato = true;
                break;
            case CONTROLLO_SPECIFICO:
                // controlla per il tipo specifico
                if (this.isModificatoSpecifico(unCampo)) {
                    modificato = true;
                } /* fine del blocco if */
                break;
            case ERRORE_NON_CONGRUO:
                break;
            default:
                break;
        } /* fine del blocco switch */

        /** valore di ritorno */
        return modificato;
    } /* fine del metodo */


    public static TMOra getIstanza() {
        return ISTANZA;
    } /* fine del metodo getter */

}
