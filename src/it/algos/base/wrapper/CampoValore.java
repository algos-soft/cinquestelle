/**
 * Title:     CampoValore
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      25-nov-2004
 */
package it.algos.base.wrapper;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;

/**
 * Wrapper per il recupero dati da Modello .
 * </p>
 * Questa classe concreta: <ul>
 * <li> Costruisce un tipo di dati </li>
 * <li> Raggruppa questi dati in un wrapper </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 25-nov-2004 ore 11.04.56
 */
public final class CampoValore extends Object {

    /**
     * campo di riferimento
     */
    private Campo campo = null;

    /**
     * nome interno del campo di riferimento
     * usato in alternativa all'oggetto campo
     * risolto in fase di esecuzione della query
     * usando il modulo della query
     */
    private String nomeCampo = null;

    /**
     * valore a livello Business Logic
     */
    private Object valore = null;


    /**
     * Costruttore base senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public CampoValore() {
        /* rimanda al costruttore di questa classe */
        this((Campo)null, null);
    }// fine del metodo costruttore base


    /**
     * Costruttore con parametri.
     * <p/>
     * Usa il valore del campo
     *
     * @param campo
     */
    public CampoValore(Campo campo) {
        /* rimanda al costruttore di questa classe */
        this(campo, campo.getValore());
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri. <br>
     *
     * @param campo
     * @param valore
     */
    public CampoValore(Campo campo, int valore) {
        /* rimanda al costruttore di questa classe */
        this(campo, new Integer(valore));
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri. <br>
     *
     * @param campo
     * @param valore
     */
    public CampoValore(Campo campo, boolean valore) {
        /* rimanda al costruttore di questa classe */
        this(campo, new Boolean(valore));
    }// fine del metodo costruttore


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param campo
     * @param valore
     */
    public CampoValore(Campo campo, Object valore) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setCampo(campo);
        this.setValore(valore);
    }// fine del metodo costruttore completo


    /**
     * Costruttore con parametri. <br>
     *
     * @param nomeCampo il nome interno del campo
     * @param valore
     */
    public CampoValore(String nomeCampo, int valore) {
        /* rimanda al costruttore di questa classe */
        this(nomeCampo, new Integer(valore));
    }// fine del metodo costruttore


    /**
     * Costruttore con parametri. <br>
     *
     * @param nomeCampo il nome interno del campo
     * @param valore
     */
    public CampoValore(String nomeCampo, boolean valore) {
        /* rimanda al costruttore di questa classe */
        this(nomeCampo, new Boolean(valore));
    }// fine del metodo costruttore


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param nomeCampo il nome interno del campo
     * @param valore
     */
    public CampoValore(String nomeCampo, Object valore) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setNomeCampo(nomeCampo);
        this.setValore(valore);
    }// fine del metodo costruttore completo


    /**
     * Costruttore completo con parametri. <br>
     *
     * @param campo dalla Enum Campi
     * @param valore da assegnare
     */
    public CampoValore(Campi campo, Object valore) {

        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            String nome = campo.get();
            this.setNomeCampo(nome);
            this.setValore(valore);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo costruttore completo


    /**
     * Risolve il nome di campo in campo, a fronte di un modulo.
     * <p/>
     *
     * @param modulo il modulo a fronte del quale risolvere il nome
     */
    public void risolvi(Modulo modulo) {
        /* variabili e costanti locali di lavoro */
        String nome;
        Campo campo;

        try {    // prova ad eseguire il codice
            if (this.getCampo() == null) {
                nome = this.getNomeCampo();
                if (Lib.Testo.isValida(nome)) {
                    if (modulo.isEsisteCampo(nome)) {
                        campo = modulo.getCampo(nome);
                        this.setCampo(campo);
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna true se questo oggetto contiene il campo calcolato.
     * <p/>
     *
     * @return true se contiene un campo calcolato
     */
    public boolean isCalcolato() {
        /* variabili e costanti locali di lavoro */
        boolean calcolato = false;
        Campo campo;

        try {    // prova ad eseguire il codice
            campo = this.getCampo();
            if (campo != null) {
                calcolato = campo.isCalcolato();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return calcolato;
    }


    /**
     * Overriding del metodo equals()
     * <p/>
     * Due CampiValore sono uguali se fanno riferimento allo stesso campo
     * (campi che soddisfano equals())
     */
    public boolean equals(Object object) {
        /* variabili e costanti locali di lavoro */
        boolean uguale = false;
        CampoValore altroCV;
        Campo campoQuesto;
        Campo campoAltro;

        try { // prova ad eseguire il codice
            if (object != null) {
                if (object instanceof CampoValore) {
                    altroCV = (CampoValore)object;
                    campoAltro = altroCV.getCampo();
                    if (campoAltro != null) {
                        campoQuesto = this.getCampo();
                        if (campoQuesto != null) {
                            uguale = campoQuesto.equals(campoAltro);
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return uguale;
    }


    /**
     * Overriding del metodo toString()
     * <p/>
     * il nome del campo e la stringa del valore
     */
    public String toString() {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        Campo campo;
        Object valore;

        try { // prova ad eseguire il codice
            campo = this.getCampo();
            if (campo != null) {
                stringa = campo.toString();
            } else {
                stringa = this.getNomeCampo();
            }// fine del blocco if-else

            valore = this.getValore();
            stringa += ", ";
            if (valore != null) {
                stringa += valore.toString();
            } else {
                stringa += "null";
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;

    }


    public Campo getCampo() {
        return campo;
    }


    public void setCampo(Campo campo) {
        this.campo = campo;
    }


    public String getNomeCampo() {
        return nomeCampo;
    }


    private void setNomeCampo(String nomeCampo) {
        this.nomeCampo = nomeCampo;
    }


    public Object getValore() {
        return valore;
    }


    public void setValore(Object valore) {
        this.valore = valore;
    }

}// fine della classe
