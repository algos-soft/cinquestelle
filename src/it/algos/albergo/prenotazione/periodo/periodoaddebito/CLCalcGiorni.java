/**
 * Title:     CLCalcGiorni
 * Copyright: Copyright (c) 2008
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      22-apr-2008
 */
package it.algos.albergo.prenotazione.periodo.periodoaddebito;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.logica.CLCalcolato;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.wrapper.Campi;

import java.util.ArrayList;
import java.util.Date;

/**
 * Logica del campo calcolato Giorni di AddebitoPeriodo.
 * </p>
 * Osserva due campi data.
 * Il risultato del calcolo è: differenza date +1:
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 22-apr-2008 ore 14.46.57
 */
public final class CLCalcGiorni extends CLCalcolato {

    private Campi campo1;

    private Campi campo2;


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     * @param campo1 primo campo (data inizio)
     * @param campo2 secondo campo (data fine)
     */
    public CLCalcGiorni(Campo unCampoParente, Campi campo1, Campi campo2) {
        /* rimanda al costruttore della superclasse */
        super(unCampoParente);

        this.campo1 = campo1;
        this.campo2 = campo2;

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

            /* registra i campi osservati */
            nomiOsservati = new ArrayList<String>();
            nomiOsservati.add(campo1.get());
            nomiOsservati.add(campo2.get());
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
        ArrayList<Object> valori;
        Object valore;

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
        int risultato = 0;
        Object valore;
        Date data1 = null;
        Date data2 = null;
        boolean continua;

        try { // prova ad eseguire il codice

            /* controllo numero di valori */
            continua = valori.size() == 2;

            /* recupero il primo valore (data) */
            if (continua) {
                continua = false;
                valore = valori.get(0);
                if (valore instanceof Date) {
                    data1 = (Date)valore;
                    continua = true;
                }// fine del blocco if
            }// fine del blocco if

            /* recupero il secondo valore (data) */
            if (continua) {
                continua = false;
                valore = valori.get(1);
                if (valore instanceof Date) {
                    data2 = (Date)valore;
                    continua = true;
                }// fine del blocco if
            }// fine del blocco if

            /* eseguo il calcolo */
            if (continua) {
                risultato = Lib.Data.diff(data2, data1);
                risultato++;
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return risultato;
    }


}// fine della classe