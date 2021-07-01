/**
 * Title:     TMEuro
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      8-feb-2004
 */
package it.algos.base.campo.tipodati.tipomemoria;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;

import java.math.BigDecimal;

/**
 * Euro.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Singleton</b>  <br>
 * <li> Definisce un modello dati per il tipo Memoria </li>
 * <li>  </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 8-feb-2004 ore 17.24.42
 */
public final class TMEuro extends TMBase {

    /**
     * istanza unica di questa classe, creata ad avvio del programma
     */
    private final static TMEuro ISTANZA = new TMEuro();


    /**
     * Costruttore completo senza parametri.<br>
     */
    public TMEuro() {
        /* rimanda al costruttore della superclasse */
        super();

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
        try {    // prova ad eseguire il codice
            this.setClasse(new BigDecimal(0).getClass()); //TODO manca
            this.setValoreVuoto(new BigDecimal(0)); //TODO manca
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * metodo getter per ottenere il valore della variabile statica privata
     */
    public static TMEuro getIstanza() {
        return ISTANZA;
    } // fine del metodo getter


    /**
     * Controlla se un campo e' stato modificato
     * per questo tipo specifico di dati Memoria
     * Se si arriva qui, gli oggetti valore Memoria e Backup sono entrambi
     * non nulli, della stessa classe e della classe di questo tipo Memoria
     *
     * @param unCampo il campo da controllare
     *
     * @return true se e' modificato
     */
    private boolean isModificatoSpecifico(Campo unCampo) {
        /* variabili e costanti locali di lavoro */
        boolean modificato = false;
        Object unOggettoMemoria = null;
        Object unOggettoBackup = null;

        /* recupera il valore della memoria */
        unOggettoMemoria = unCampo.getCampoDati().getMemoria();

        /* recupera il valore del backup */
        unOggettoBackup = unCampo.getCampoDati().getBackup();

        /* controlla se sono diversi */

        return modificato;
    } /* fine del metodo */


    /**
     * Controlla se un campo e' modificato
     * confronta il valore memoria col valore backup
     *
     * @param unCampo il campo da controllare
     *
     * @return true se e' modificato
     */
    public boolean isCampoModificato(Campo unCampo) {
        /* variabili e costanti locali di lavoro */
        boolean modificato = false;
        int unCodice = 0;

        /* controlla se e' modificato nella superclasse
         *  (controlla eventuali valori nulli) */
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
}// fine della classe
