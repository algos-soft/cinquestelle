/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      12-apr-2005
 */
package it.algos.base.azione.dialogo;

import it.algos.base.azione.Azione;
import it.algos.base.azione.adapter.AzAdapterAction;
import it.algos.base.errore.Errore;
import it.algos.base.gestore.Gestore;
import it.algos.base.gestore.GestoreDialogo;

import java.awt.event.ActionEvent;

/**
 * Dialogo.
 * <p/>
 * Questa classe azione concreta: <ul>
 * <li> @TODO DESCRIZIONE SINTETICA DELL'AZIONE </li>
 * <li> Implementa il metodo <code>actionPerformed</code> della interfaccia
 * <code>ActionListener</code> </li>
 * <li> Viene usata nei menu, nelle toolbar e nei bottoni di comando </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 12-apr-2005 ore 14.21.00
 */
public final class AzDialogo extends AzAdapterAction {

    /**
     * Testo della chiave per recuperare l'azione (obbligatorio)
     */
    private static final String CHIAVE = Azione.DIALOGO;

    /**
     * Testo di default da mostrare
     * (obbligatorio per le azioni dei menu/bottoni/toolbar)
     */
    private static final String NOME = "dialogo";

    /**
     * Nome del disegno di default da mostrare
     * (consigliato per le azioni dei menu/bottoni/toolbar)
     */
    private static final String ICONA_PICCOLA = "";

    private static final String ICONA_MEDIA = "";

    private static final String ICONA_GRANDE = "";

    /**
     * Descrizione di default per il tooltiptext (facoltativo)
     */
    private static final String TESTO_TIP = "dialogo";

    /**
     * Descrizione di default per l'aiuto in linea (facoltativo)
     */
    private static final String TESTO_HELP = "dialogo";

    /**
     * Carattere del tasto acceleratore (facoltativo)
     */
    private static final char ACCELERATORE = ' ';

    /**
     * codice carattere di default per il tasto mnemonico (facoltativo)
     */
    private static final int MNEMONICO = 0;

    /**
     * Lettera di default per il tasto comando (facoltativo)
     */
    private static final String COMANDO = null;

    /**
     * Costante per l'azione attiva (booleana)
     */
    private static final boolean ATTIVA = true;

    /**
     * Costante per l'azione abilitata alla partenza (booleana)
     */
    private static final boolean ABILITATA = true;


    /**
     * Costruttore completo senza parametri.
     */
    public AzDialogo() {
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
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* regola le variabili*/
        super.setChiave(CHIAVE);
        super.setNome(NOME);
        super.setIconaPiccola(ICONA_PICCOLA);
        super.setIconaMedia(ICONA_MEDIA);
        super.setIconaGrande(ICONA_GRANDE);
        super.setTooltip(TESTO_TIP);
        super.setHelp(TESTO_HELP);
        super.setCarattereAcceleratore(ACCELERATORE);
        super.setCarattereMnemonico(MNEMONICO);
        super.setCarattereComando(COMANDO);
        super.setAttiva(ATTIVA);
        super.setAbilitataPartenza(ABILITATA);
    }// fine del metodo inizia


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
        /* variabili e costanti locali di lavoro */
        Gestore gestore;
        GestoreDialogo gestoreDialogo;

        try { // prova ad eseguire il codice
            /* recupera il riferimento al gestore standard */
            gestore = super.getGestore();

            /* esegue solo se il gestore è specifico */
            if (gestore instanceof GestoreDialogo) {
                /* recupera il riferimento al gestore specifico */
                gestoreDialogo = (GestoreDialogo)gestore;

                /* invoca il metodo delegato della classe gestione eventi */
                gestoreDialogo.azioneDialogo(unEvento, this);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

}// fine della classe
