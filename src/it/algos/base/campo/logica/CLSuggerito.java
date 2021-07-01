/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      6-apr-2006
 */
package it.algos.base.campo.logica;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.db.CampoDB;
import it.algos.base.errore.Errore;
import it.algos.base.evento.BaseListener;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;

/**
 * Suggerisce il valore preso da un campo di un'altra tavola.
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 6-apr-2006 ore 14.51.57
 */
public final class CLSuggerito extends CLBase {


    /**
     * campo che lancia l'evento
     */
    private String campoOsservato;

    /**
     * campo suggerito nel modulo linkato dal campo osservato
     */
    private String campoSuggerito;


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CLSuggerito(Campo unCampoParente) {
        /* rimanda al costruttore della superclasse */
        super(unCampoParente);

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
    } /* fine del metodo inizia */


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alle impostazioni
     * correnti delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto (di norma) <br>
     * Viene eseguito una sola volta <br>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void inizializza() {
        try { // prova ad eseguire il codice
            /* crea l'azione da associare al campo osservato */
            this.setAzione(new AzioneCalcolata());

            /* invoca il metodo sovrascritto della superclasse */
            super.inizializza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola l'azione.
     * <p/>
     * Metodo invocato dal ciclo inizializza del Form <br>
     * Associa l'azione al componente dei campi osservati <br>
     */
    public void regolaAzione() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        BaseListener azione;
        String campoOsservato;
        Campo campo;

        try {    // prova ad eseguire il codice
            /* recupera l'azione interna */
            azione = this.getAzione();
            continua = (azione != null);

            /* recupera il nome del campo interessato */
            /* recupera il campo */
            /* aggiunge l'azione */
            if (continua) {
                campoOsservato = this.getCampoOsservato();
                campo = this.getCampo(campoOsservato);
                if (campo != null) {
                    campo.addListener(azione);
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Esegue l'operazione prevista.
     */
    public void esegui() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Campo campo;
        CampoDB campoDB = null;
        Modulo modLinkato = null;
        String suggerito = "";
        int cod = 0;
        Object valore = null;
        Object valorePreesistente;

        try { // prova ad eseguire il codice
            /* recupera il campo osservato ed il modulo linkato */
            campo = this.getCampo(campoOsservato);
            continua = (campo != null);

            if (continua) {
                campoDB = campo.getCampoDB();
                continua = (campoDB != null);
            }// fine del blocco if

            if (continua) {
                modLinkato = campoDB.getModuloLinkato();
                continua = (modLinkato != null);
            }// fine del blocco if

            if (continua) {
                suggerito = this.getCampoSuggerito();
                continua = Lib.Testo.isValida(suggerito);
            }// fine del blocco if

            if (continua) {
                cod = (Integer)campo.getValore();
                continua = (cod != 0);
            }// fine del blocco if

            if (continua) {
                valore = modLinkato.query().valoreCampo(suggerito, cod);
                continua = (valore != null);
            }// fine del blocco if

            if (continua) {
                valorePreesistente = this.getCampoParente().getValore();
                continua = Lib.Testo.isVuota(valorePreesistente);
            }// fine del blocco if

            /* registra il valore ottenuto solo se il campo destinazione è vuoto*/
            if (continua) {
                this.getCampoParente().setValore(valore);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public String getCampoOsservato() {
        return campoOsservato;
    }


    public void setCampoOsservato(String campoOsservato) {
        this.campoOsservato = campoOsservato;
    }


    public String getCampoSuggerito() {
        return campoSuggerito;
    }


    public void setCampoSuggerito(String campoSuggerito) {
        this.campoSuggerito = campoSuggerito;
    }
}// fine della classe
