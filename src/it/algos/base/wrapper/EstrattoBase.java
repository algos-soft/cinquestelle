/**
 * Title:     Estratto
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      11-feb-2004
 */
package it.algos.base.wrapper;

import it.algos.base.errore.Errore;
import it.algos.base.matrice.MatriceDoppia;
import it.algos.base.pannello.Pannello;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Contenitore di informazioni di un singolo record restituite dal Modello.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Costruisce un tipo di dati </li>
 * <li> Raggruppa questi dati in un wrapper </li>
 * <li> </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 11-feb-2004 ore 15.11.19
 */
public final class EstrattoBase {

    /**
     * tipo di utilizzo di default
     */
    public static final Tipo DEFAULT = Tipo.stringa;

    /**
     * estratto generico
     */
    private Object oggetto = null;

    /**
     * estratto di una stringa
     */
    private String stringa = "";

    /**
     * estratto di un pannello grafico
     */
    private Pannello pannello = null;


    /**
     * matrice doppia (codici - valori)
     */
    private MatriceDoppia matrice = null;

    /**
     * estratto di una lista di stringhe
     */
    private ArrayList<String> listaStringhe = null;

    /**
     * estratto di una icona
     */
    private ImageIcon icona = null;

    /**
     * tipo di variabile utilizzata dall'estratto
     */
    private Tipo tipo;


    /**
     * Costruttore senza parametri. <br>
     */
    public EstrattoBase() {
        /* rimanda al costruttore di questa classe */
        this(null, DEFAULT);
    }// fine del metodo costruttore base


    /**
     * Costruttore con parametri. <br>
     *
     * @param unOggetto stringa, pannello, lista di stringhe o icona
     */
    public EstrattoBase(Object unOggetto) {
        /* rimanda al costruttore di questa classe */
        this(unOggetto, DEFAULT);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param unOggetto stringa, pannello, lista di stringhe o icona
     * @param tipo di oggetto gestito
     */
    public EstrattoBase(Object unOggetto, Tipo tipo) {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regola le variabili di istanza coi parametri */
            this.setTipo(tipo);

            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia(unOggetto);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @param oggetto stringa, pannello o lista di stringhe
     *
     * @throws Exception unaEccezione
     */
    private void inizia(Object oggetto) throws Exception {
        /* variabili e costanti locali di lavoro */
        Tipo tipo;

        try { // prova ad eseguire il codice
            /* recupera il tipo previsto per questo estratto */
            tipo = this.getTipo();

            /* selettore della variabile */
            switch (tipo) {
                case stringa:
                    this.setStringa((String)oggetto);
                    break;
                case pannello:
                    this.setPannello((Pannello)oggetto);
                    break;
                case oggetto:
                    this.setOggetto(oggetto);
                    break;
                case matrice:
                    this.setMatrice((MatriceDoppia)oggetto);
                    break;
                case lista:
                    this.setListaStringhe((ArrayList)oggetto);
                    break;
                default: // caso non definito
                    this.setOggetto(oggetto);
                    break;
            } // fine del blocco switch
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    public Object getOggetto() {
        return oggetto;
    }


    private void setOggetto(Object oggetto) {
        this.oggetto = oggetto;
    }


    public String getStringa() {
        return stringa;
    }


    private void setStringa(String stringa) {
        this.stringa = stringa;
    }


    public Pannello getPannello() {
        return pannello;
    }


    private void setPannello(Pannello pannello) {
        this.pannello = pannello;
    }


    public MatriceDoppia getMatrice() {
        return matrice;
    }


    private void setMatrice(MatriceDoppia matrice) {
        this.matrice = matrice;
    }


    public ArrayList<String> getListaStringhe() {
        return listaStringhe;
    }


    private void setListaStringhe(ArrayList<String> listaStringhe) {
        this.listaStringhe = listaStringhe;
    }


    public ImageIcon getIcona() {
        return icona;
    }


    private void setIcona(ImageIcon icona) {
        this.icona = icona;
    }


    public Tipo getTipo() {
        return tipo;
    }


    private void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }


    /**
     * Classe interna Enumerazione.
     */
    public enum Tipo {

        stringa,
        pannello,
        oggetto,
        matrice,
        lista;


        /**
         * Oggetto della enumeration.
         */
        public static Tipo get(int pos) {
            /* variabili e costanti locali di lavoro */
            Tipo oggetto = null;

            try { // prova ad eseguire il codice
                /* traverso tutta la collezione */
                for (Tipo tipoVar : Tipo.values()) {
                    if ((tipoVar.ordinal() + 1 == pos)) {
                        oggetto = tipoVar;
                        break;
                    }// fine del blocco if
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return oggetto;
        }


        /**
         * Oggetto della enumeration.
         */
        public static Tipo get(String nome) {
            /* variabili e costanti locali di lavoro */
            Tipo oggetto = null;

            try { // prova ad eseguire il codice
                /* traverso tutta la collezione */
                for (Tipo tipoVar : Tipo.values()) {
                    if (tipoVar.toString().equals(nome)) {
                        oggetto = tipoVar;
                        break;
                    }// fine del blocco if
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return oggetto;
        }


        /**
         * Lista di nomi per creazione popup.
         */
        public static ArrayList<String> getLista() {
            /* variabili e costanti locali di lavoro */
            ArrayList<String> lista = null;

            try { // prova ad eseguire il codice
                lista = new ArrayList<String>();

                /* traverso tutta la collezione */
                for (Tipo campo : Tipo.values()) {
                    lista.add(campo.toString());
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }

    }// fine della classe

}// fine della classe
