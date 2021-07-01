/**
 * Title:     CVDBottone2stati
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      12-giu-2006
 */
package it.algos.base.campo.video.decorator;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.video.CampoVideo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;

import javax.swing.*;

/**
 * Decoratore grafico del campo video.
 * <p/>
 * Aggiunge al campo un bottone con icona di lucchetto aperto o chiuso
 * Quando il lucchetto viene chiuso il campo viene reso non modificabile
 * Quando il lucchetto viene aperto il campo viene reso modificabile
 * Quando il campo non è modificabile il lucchetto si può comunque
 * premere (se il campo non è disabilitato)
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  12-giu-2006 ore 9.58
 */
public final class CVDLucchetto extends CVDBottone2stati {


    /**
     * flag dello stato iniziale
     */
    private boolean chiuso;

    /**
     * icona per il primo stato
     */
    private final static String nomeIconaStato0 = "lucchettoaperto";

    /**
     * icona per il secondo stato
     */
    private final static String nomeIconaStato1 = "lucchettochiuso";


    /**
     * Costruttore completo con parametri.
     *
     * @param campoVideo da decorare
     * @param chiuso se il lucchetto deve essere inizialmente chiuso
     */
    CVDLucchetto(CampoVideo campoVideo, boolean chiuso) {
        /* rimanda al costruttore della superclasse */
        super(campoVideo, null, null);

        try { // prova ad eseguire il codice

            /* regola le variabili di istanza coi parametri */
            this.setChiuso(chiuso);

            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        try { // prova ad eseguire il codice

            this.setStato(this.isChiuso());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Crea gli elementi GUI del pannelloCampo.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Gli elementi possono essere anche più di uno <br>
     * Gli elementi vengono aggiunti al pannelloCampo <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see #regolaElementi()
     */
    protected void creaComponentiGUI() {

        try { // prova ad eseguire il codice

            /* invoca il metodo sovrascritto della superclasse */
            super.creaComponentiGUI();

            this.setIconaStato0(Lib.Risorse.getIconaBase(nomeIconaStato0));
            this.setIconaStato1(Lib.Risorse.getIconaBase(nomeIconaStato1));

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Metodo invocato quando si preme il bottone.
     * <p/>
     * Inverte lo stato del lucchetto e posiziona
     * il fuoco sul campo
     */
    @Override protected void esegui() {
        /* variabili e costanti locali di lavoro */
        boolean oldStato;
        boolean newStato;
        Campo campo;

        super.esegui();

        try { // prova ad eseguire il codice

            /*
            * abilita o disabilita il campo in funzione
            * dello stato del lucchetto
            */
            oldStato = this.getStato();
            newStato = !this.getStato();
            campo = this.getCampoParente();
            campo.setModificabile(newStato);

            /* quando viene sbloccato prende anche il fuoco */
            if ((newStato) && (newStato != oldStato)) {
                campo.grabFocus();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Abilita o disabilita il componente.
     * <p/>
     * Sovrascrive il metodo della superclasse.<br>
     * Riabilita il lucchetto se il campo non è disabilitato.<br>
     *
     * @param flag per abilitare o disabilitare
     */
    public void setModificabile(boolean flag) {
        /* variabili e costanti locali di lavoro */
        JButton bottone;

        try { // prova ad eseguire il codice

            super.setModificabile(flag);

            /* se il campo è abilitato il bottone lucchetto è sempre abilitato */
            if (this.getCampoParente().isAbilitato()) {
                bottone = this.getBottone();
                if (bottone != null) {
                    bottone.setEnabled(true);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    private boolean isChiuso() {
        return chiuso;
    }


    private void setChiuso(boolean chiuso) {
        this.chiuso = chiuso;
    }


}// fine della classe
