/**
 * Title:     LibRistorante
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      03-mar-2005
 */
package it.algos.albergo.ristorante;

import it.algos.albergo.ristorante.lingua.Lingua;
import it.algos.albergo.ristorante.lingua.LinguaModulo;
import it.algos.albergo.ristorante.piatto.Piatto;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.progetto.Progetto;


/**
 * Repository di funzionalita' specifiche comuni al package Ristorante.
 * </p>
 * Classe astratta con soli metodi statici <br>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 03-mar-2005 ore 18.31.24
 */
public abstract class LibRistorante extends Object {

    /**
     * Costruisce la stringa per la visualizzazione di un
     * piatto con contorno nella lingua principale.
     * <p/>
     *
     * @param codPiatto il codice chiave del piatto
     * @param codContorno il codice chiave del contorno
     *
     * @return la stringa con piatto - congiunzione - contorno
     */
    public static String stringaPiattoContorno(int codPiatto, int codContorno) {
        /* variabili locali di lavoro */
        String nomeCompleto = "";
        String nomePiatto = "";
        String nomeContorno = "";
        String congiunzione = "";
        LinguaModulo moduloLingua = null;

        try { // prova ad eseguire il codice

            nomePiatto = getNomePiatto(codPiatto);
            nomeContorno = getNomePiatto(codContorno);

            /* costruisco il nome completo */
            nomeCompleto += nomePiatto;
            if (Lib.Testo.isValida(nomeContorno)) {

                moduloLingua = (LinguaModulo)Progetto.getModulo(Lingua.NOME_MODULO);
                congiunzione = moduloLingua.getLogica().getCongiunzione();

                nomeContorno = Lib.Testo.primaMinuscola(nomeContorno);
                nomeCompleto += " " + congiunzione + " ";
                nomeCompleto += nomeContorno;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nomeCompleto;
    }


    /**
     * Recupera il nome di un piatto dal codice.
     * <p/>
     *
     * @param codice il codice del piatto
     *
     * @return il nome del piatto
     */
    private static String getNomePiatto(int codice) {
        /* variabili e costanti locali di lavoro */
        String nome = "";
        Object valore = null;
        Modulo moduloPiatto = null;

        try {    // prova ad eseguire il codice
            if (codice > 0) {
                moduloPiatto = Progetto.getModulo(Ristorante.MODULO_PIATTO);
                valore = moduloPiatto.query().valoreCampo(Piatto.CAMPO_NOME_ITALIANO, codice);
                if (valore != null) {
                    if (valore instanceof String) {
                        nome = (String)valore;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nome;
    }

}// fine della classe
