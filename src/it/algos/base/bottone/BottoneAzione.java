/**
 * Copyright(c): 2005
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 3-4-05
 */

package it.algos.base.bottone;

import it.algos.base.azione.Azione;
import it.algos.base.azione.adapter.AzAdapterAction;
import it.algos.base.errore.Errore;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Method;

/**
 * Bottone per gestione eventi.
 * </p>
 * Questa classe: <ul>
 * <li> Riceve un'istanza della classe che crea il bottone </li>
 * <li> Riceve il nome del metodo da invocare alla pressione del bottone </li>
 * <li> Costruisce un'azione </li>
 * <li> Mantiene il metodo actionPerformed </li>
 * <li>  </li>
 * <li>  </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 3-4-05
 */
public class BottoneAzione extends JButton {

    /**
     * classe proprietaria di questo bottone
     */
    private Object oggetto;

    /**
     * nome del metodo invocato dal bottone
     */
    private String nomeMetodo;

    /**
     * metodo invocato dal bottone
     */
    private Method metodo;

    /**
     * azione associata al bottone
     */
    private Azione azione;

    /**
     * testo visibile del bottone
     */
    private String testo;


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param oggetto proprietario di questo bottone
     * @param metodo invocato dal bottone
     */
    public BottoneAzione(Object oggetto, String metodo) {
        /* rimanda al costruttore completo */
        this(oggetto, metodo, "");
    }// fine del metodo costruttore


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param oggetto proprietario di questo bottone
     * @param metodo invocato dal bottone
     * @param testo visibile del bottone
     */
    public BottoneAzione(Object oggetto, String metodo, String testo) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setOggetto(oggetto);
        this.setNomeMetodo(metodo);
        this.setTesto(testo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* opacità di default */
        this.setOpaque(false);

        /* Regola il bottone */
        this.regolaBottone();

        /* Regola il metodo da eseguire */
        this.regolaMetodo();
    }


    /**
     * Regola il bottone.
     * <p/>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     */
    private void regolaBottone() {
        /* variabili e costanti locali di lavoro */
        Azione azione;

        try { // prova ad eseguire il codice

            /* costruisce l'azione e la assegna al bottone */
            this.setAzione(new AzioneBottone());
            azione = this.getAzione();
            this.setAction(azione.getAzione());

            /* regola il testo del bottone */
            this.setText(this.getTesto());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola il metodo da eseguire.
     * <p/>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Recupera l'oggetto proprietario del bottone <br>
     * Recupera la classe dell'oggetto <br>
     * Recupera tutti i metodi della classe <br>
     * Recupera il metodo da eseguire, dal nome ricevuto <br>
     */
    private void regolaMetodo() {
        /* variabili e costanti locali di lavoro */
        Object oggetto;
        Class classe;
        String nomeMetodo;
        Method metodo;

        try { // prova ad eseguire il codice
            /* recupera l'istanza della classe */
            oggetto = this.getOggetto();

            /* recupera la classe */
            classe = oggetto.getClass();

            /* recupera il nome ricevuto del metodo */
            nomeMetodo = this.getNomeMetodo();

            /* recupera il metodo dalla classe */
//            metodo = classe.getDeclaredMethod(nomeMetodo, (Class[])null);
            metodo = classe.getDeclaredMethod(nomeMetodo, ActionEvent.class);
            this.setMetodo(metodo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Esegue il comando dell'evento.
     * <p/>
     * Metodo invocato dalla pressione sul bottone <br>
     * Recupera l'oggetto proprietario del bottone <br>
     * Recupera il metodo da eseguire, associato al bottone <br>
     * Invoca il metodo della classe <br>
     *
     * @param unEvento evento che causa l'azione da eseguire <br>
     */
    private void invoca(ActionEvent unEvento) {
        /* variabili e costanti locali di lavoro */
        Object oggetto;
        Method metodo;

        try { // prova ad eseguire il codice
            /* recupera l'istanza della classe */
            oggetto = this.getOggetto();

            /* recupera il metodo da eseguire */
            metodo = this.getMetodo();

            /* invoca il metodo della classe */
            if (metodo != null) {
                metodo.invoke(oggetto, unEvento);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    private Object getOggetto() {
        return oggetto;
    }


    private void setOggetto(Object oggetto) {
        this.oggetto = oggetto;
    }


    private String getNomeMetodo() {
        return nomeMetodo;
    }


    private void setNomeMetodo(String nomeMetodo) {
        this.nomeMetodo = nomeMetodo;
    }


    private Method getMetodo() {
        return metodo;
    }


    private void setMetodo(Method metodo) {
        this.metodo = metodo;
    }


    public Azione getAzione() {
        return azione;
    }


    private void setAzione(Azione azione) {
        this.azione = azione;
    }


    private String getTesto() {
        return testo;
    }


    public void setTesto(String testo) {
        this.testo = testo;
    }


    /**
     * Inner class per gestire l'azione del bottone.
     */
    private class AzioneBottone extends AzAdapterAction {

        /**
         * actionPerformed, da ActionListener.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato di questa classe */
                invoca(unEvento);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


} // fine della classe
