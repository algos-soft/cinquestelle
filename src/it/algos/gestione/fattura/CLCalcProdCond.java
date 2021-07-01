/**
 * Title:     CLProdCond
 * Copyright: Copyright (c) 2007
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      20-feb-2007
 */
package it.algos.gestione.fattura;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.logica.CLCalcolato;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;

import java.util.ArrayList;

/**
 * Logica dei campi calcolati Importo Rivalsa e Importo R.A.
 * </p>
 * Osserva due campi numerici e un campo booleano.
 * Effettua una moltiplicazione 'booleana':
 * <p/>
 * Opera in funzione dello stato del campo booleano:
 * - se è true, il risultato è la moltiplicazione dei due campi double
 * - se è false, il risultato è zero
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 20-feb-2007 ore 14.51.57
 */
public final class CLCalcProdCond extends CLCalcolato {

    private FattBase.Cam campo1;

    private FattBase.Cam campo2;

    private FattBase.Cam campoBool;


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     * @param campo1 primo campo numerico
     * @param campo2 secondo campo numerico
     * @param campoBool campo booleano
     */
    public CLCalcProdCond(Campo unCampoParente,
                          FattBase.Cam campo1,
                          FattBase.Cam campo2,
                          FattBase.Cam campoBool) {
        /* rimanda al costruttore della superclasse */
        super(unCampoParente);

        this.campo1 = campo1;
        this.campo2 = campo2;
        this.campoBool = campoBool;

        try { // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        ArrayList<String> nomiOsservati;
        Campo campoParente;

        try { // prova ad eseguire il codice

            /* regolazioni standard */
            campoParente = this.getCampoParente();
            campoParente.getCampoDB().setCampoFisico(true);
            campoParente.setAbilitato(false);

            /* registra i campi osservati
             * - il campo importo rivalsa INPS */
            nomiOsservati = new ArrayList<String>();
            nomiOsservati.add(campo1.get());
            nomiOsservati.add(campo2.get());
            nomiOsservati.add(campoBool.get());
            this.setCampiOsservati(nomiOsservati);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo inizia */


    /**
     * Esegue l'operazione prevista.
     */
    public void esegui() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Object> valori = null;
        Object valore = null;

        try { // prova ad eseguire il codice

            /* recupera i valori dei campi */
            valori = this.getValoriOsservati();

            /* esegue l'operazione */
            valore = esegueOperazione(valori);

            /* registra il valore ottenuto */
            this.getCampoParente().setValore(valore);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Esegue l'operazione prevista.
     * <p/>
     * Se il campo flagImponibile è
     * Riceve una lista di valori arbitrari (devono essere dello stesso tipo) <br>
     * Recupera dal campo il tipo di operazione da eseguire <br>
     * Esegue l'operazione su tutti i valori <br>
     * restituisce il valore risultante <br>
     *
     * @param valori in ingresso
     *
     * @return risultato dell'operazione
     */
    public Object esegueOperazione(ArrayList<Object> valori) {
        /* variabili e costanti locali di lavoro */
        Double risultato = 0.0; // ritorno di default se il booleano è spento
        Object valore;
        double valore1 = 0;
        double valore2 = 0;
        boolean valoreBool = false;
        int numDec;
        boolean continua;

        try { // prova ad eseguire il codice

            /* controllo numero di valori */
            continua = valori.size() == 3;

            /* recupero il terzo valore (booleano) */
            if (continua) {
                continua = false;
                valore = valori.get(2);
                if (valore instanceof Boolean) {
                    valoreBool = (Boolean)valore;
                    continua = true;
                }// fine del blocco if
            }// fine del blocco if

            /* controllo se il booleano è true */
            if (continua) {
                continua = valoreBool;
            }// fine del blocco if

            /* recupero il primo valore (numerico) */
            if (continua) {
                continua = false;
                valore = valori.get(0);
                if (valore instanceof Double) {
                    valore1 = (Double)valore;
                    continua = true;
                }// fine del blocco if
            }// fine del blocco if

            /* recupero il secondo valore (numerico) */
            if (continua) {
                continua = false;
                valore = valori.get(1);
                if (valore instanceof Double) {
                    valore2 = (Double)valore;
                    continua = true;
                }// fine del blocco if
            }// fine del blocco if

            /* eseguo il calcolo */
            if (continua) {
                risultato = valore1 * valore2;
                numDec = this.getCampoParente().getCampoDati().getNumDecimali();
                risultato = Lib.Mat.arrotonda(risultato, numDec);
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return risultato;
    }


}// fine della classe
