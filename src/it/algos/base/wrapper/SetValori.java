/**
 * Title:     SetValori
 * Copyright: Copyright (c) 2007
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      8-mar-2007
 */
package it.algos.base.wrapper;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Wrapper che mantiene una mappa di oggetti CampoValore per le query .
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 8-mar-2007 ore 14.30.00
 */
public final class SetValori extends LinkedHashMap<String, CampoValore> {

    /**
     * modulo di riferimento della query
     */
    private Modulo modulo = null;


    /**
     * Costruttore senza parametri.
     * <p/>
     *
     * @param modulo di riferimento
     */
    public SetValori(Modulo modulo) {
        /* rimanda al costruttore di questa classe */
        this(modulo, null);
    }// fine del metodo costruttore


    /**
     * Costruttore con lista di oggetti CampoValore.
     * <p/>
     *
     * @param modulo di riferimento
     * @param lista di oggetti CampoValore da aggiungere
     */
    public SetValori(Modulo modulo, ArrayList<CampoValore> lista) {

        this.setModulo(modulo);

        try { // prova ad eseguire il codice
            if (lista != null) {
                for (CampoValore cv : lista) {
                    this.add(cv);
                }
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Aggiunge un campo con relativo valore.
     * <p/>
     *
     * @param campo il campo
     * @param valore il valore
     */
    public void add(Campo campo, Object valore) {
        this.add(new CampoValore(campo, valore));
    }


    /**
     * Aggiunge un campo con relativo valore.
     * <p/>
     *
     * @param nome del campo
     * @param valore il valore
     */
    public void add(String nome, Object valore) {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        Modulo modulo;

        try { // prova ad eseguire il codice
            modulo = this.getModulo();
            if (modulo != null) {
                campo = modulo.getCampo(nome);
                if (campo != null) {
                    this.add(campo, valore);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Aggiunge un campo con relativo valore.
     * <p/>
     *
     * @param campo dalla enum Campi
     * @param valore il valore
     */
    public void add(Campi campo, Object valore) {
        this.add(campo.get(), valore);
    }


    /**
     * Aggiunge un CampoValore.
     * <p/>
     *
     * @param cv il CampoValore da aggiungere
     */
    public void add(CampoValore cv) {
        /* variabili e costanti locali di lavoro */
        String chiave;

        try {    // prova ad eseguire il codice
            chiave = cv.getCampo().getChiaveCampo();
            this.put(chiave, cv);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge una lista di oggetti CampoValore.
     * <p/>
     *
     * @param listaCV lista di oggetti CampoValore da aggiungere
     */
    public void add(ArrayList<CampoValore> listaCV) {
        try {    // prova ad eseguire il codice
            for (CampoValore cv : listaCV) {
                this.add(cv);
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna un oggetto CampoValore dal campo.
     * <p/>
     *
     * @param campo da cercare
     *
     * @return oggetto CampoValore
     */
    public CampoValore getCampoValore(Campo campo) {
        /* variabili e costanti locali di lavoro */
        CampoValore cv = null;
        String chiave;

        try {    // prova ad eseguire il codice
            chiave = campo.getChiaveCampo();
            cv = this.get(chiave);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return cv;
    }


    /**
     * Ritorna un oggetto CampoValore dal nome del campo.
     * <p/>
     *
     * @param nomeCampo da cercare
     *
     * @return oggetto CampoValore
     */
    public CampoValore getCampoValore(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        CampoValore cv = null;
        String chiave;

        try {    // prova ad eseguire il codice
            chiave = Lib.Camp.chiaveCampo(nomeCampo, this.getModulo());
            cv = this.get(chiave);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return cv;
    }


    /**
     * Ritorna un oggetto CampoValore da un elemento Campi.
     * <p/>
     *
     * @param campo elemento da cercare
     *
     * @return oggetto CampoValore
     */
    public CampoValore getCampoValore(Campi campo) {
        /* variabili e costanti locali di lavoro */
        CampoValore cv = null;
        String nomeCampo;

        try {    // prova ad eseguire il codice
            nomeCampo = campo.get();
            cv = this.getCampoValore(nomeCampo);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return cv;
    }


    /**
     * Verifica se questo SetValori contiene un dato campo.
     * <p/>
     *
     * @param campo del quale controllare l'esistenza
     *
     * @return true se il campo è contenuto in questo SetValori
     */
    public boolean isContieneCampo(Campo campo) {
        /* variabili e costanti locali di lavoro */
        boolean contiene = false;
        CampoValore cv;

        try {    // prova ad eseguire il codice
            cv = this.getCampoValore(campo);
            contiene = (cv != null);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return contiene;
    }


    /**
     * Verifica se questo SetValori contiene un dato campo.
     * <p/>
     *
     * @param campo del quale controllare l'esistenza
     *
     * @return true se il campo è contenuto in questo SetValori
     */
    public boolean isContieneCampo(Campi campo) {
        /* variabili e costanti locali di lavoro */
        boolean contiene = false;
        CampoValore cv;

        try {    // prova ad eseguire il codice
            cv = this.getCampoValore(campo);
            contiene = (cv != null);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return contiene;
    }


    /**
     * Ritorna il valore dal campo.
     * <p/>
     *
     * @param campo da cercare
     *
     * @return valore del campo
     */
    public Object getValore(Campo campo) {
        /* variabili e costanti locali di lavoro */
        Object valore = null;
        CampoValore cv = null;
        String chiave;

        try {    // prova ad eseguire il codice
            chiave = campo.getChiaveCampo();
            cv = this.get(chiave);
            if (cv != null) {
                valore = cv.getValore();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Ritorna il valore dal nome del campo.
     * <p/>
     *
     * @param nomeCampo da cercare
     *
     * @return valore del campo
     */
    public Object getValore(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Object valore = null;
        CampoValore cv = null;
        String chiave;

        try {    // prova ad eseguire il codice
            chiave = this.getChiave(nomeCampo);
            cv = this.get(chiave);
            if (cv != null) {
                valore = cv.getValore();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Ritorna il valore da un elemento Campi.
     * <p/>
     *
     * @param campo elemento da cercare
     *
     * @return valore del campo
     */
    public Object getValore(Campi campo) {
        /* variabili e costanti locali di lavoro */
        Object valore = null;
        String nomeCampo;

        try {    // prova ad eseguire il codice
            nomeCampo = campo.get();
            valore = this.getValore(nomeCampo);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Assegna un valore a un campo.
     * <p/>
     * Se il campo è già esistente ne sostituisce il valore.<br>
     * Se il campo non esiste lo aggiunge ora col valore fornito.<br>
     *
     * @param campo da regolare
     * @param valore da assegnare
     */
    public void setValore(Campo campo, Object valore) {
        /* variabili e costanti locali di lavoro */
        CampoValore cv;

        try {    // prova ad eseguire il codice

            /* recupera il CampoValore se esiste o lo aggiunge se non esiste */
            cv = this.getCampoValore(campo);
            if (cv == null) {
                cv = new CampoValore(campo, null);
                this.add(cv);
            }// fine del blocco if

            /* regola il valore */
            cv.setValore(valore);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Assegna un valore a un campo identificato dal nome.
     * <p/>
     * Se il campo è già esistente ne sostituisce il valore.<br>
     * Se il campo non esiste lo aggiunge ora col valore fornito.<br>
     *
     * @param nomeCampo da regolare
     * @param valore da assegnare
     */
    public void setValore(String nomeCampo, Object valore) {
        /* variabili e costanti locali di lavoro */
        String chiave;
        CampoValore cv;
        Campo campo;

        try {    // prova ad eseguire il codice

            /* recupera il CampoValore se esiste o lo aggiunge se non esiste */
            chiave = this.getChiave(nomeCampo);
            cv = this.get(chiave);
            if (cv == null) {
                campo = this.getModulo().getCampo(nomeCampo);
                cv = new CampoValore(campo, null);
                this.add(cv);
            }// fine del blocco if

            /* regola il valore */
            cv.setValore(valore);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Assegna un valore a un campo identificato da un elemento Campi.
     * <p/>
     * Se il campo è già esistente ne sostituisce il valore.<br>
     * Se il campo non esiste lo aggiunge ora col valore fornito.<br>
     *
     * @param campo da regolare
     * @param valore da assegnare
     */
    public void setValore(Campi campo, Object valore) {
        /* variabili e costanti locali di lavoro */
        String nome;

        try {    // prova ad eseguire il codice
            nome = campo.get();
            this.setValore(nome, valore);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna la lista di oggetti CampoValore.
     * <p/>
     *
     * @return la lista dei CampoValore
     */
    public ArrayList<CampoValore> getListaValori() {
        /* variabili e costanti locali di lavoro */
        ArrayList<CampoValore> lista = new ArrayList<CampoValore>();

        try {    // prova ad eseguire il codice
            for (CampoValore cv : this.values()) {
                lista.add(cv);
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Ritorna il nome chiave di un campo dato il nome.
     * <p/>
     * Si riferisce ai campi del modulo di riferimento questo set.
     *
     * @param nome del campo
     * @return il nome chiave del campo
     */
    private String getChiave(String nome) {
        /* variabili e costanti locali di lavoro */
        String chiave = "";

        try { // prova ad eseguire il codice
            chiave = Lib.Camp.chiaveCampo(nome, this.getModulo());
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return chiave;
    }


    /**
     * Si assicura che questo SetValori contenga tutti i campi (fisici) del modello.
     * <p/>
     * Eventualmente aggiunge quelli mancanti, con valore vuoto.
     */
    public void ensureAllFields () {

        try {    // prova ad eseguire il codice
            ArrayList<Campo> campi = this.getModulo().getCampiFisici();
            for(Campo campo : campi){
                if (!campo.getCampoDB().isFissoAlgos()) {
                    if (!this.isContieneCampo(campo)) {
                        this.add(campo, campo.getCampoDati().getValoreMemoriaVuoto());
                    }// fine del blocco if
                }// fine del blocco if
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }





    private Modulo getModulo() {
        return modulo;
    }


    private void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }
}// fine della classe
