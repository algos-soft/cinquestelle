/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      9-gen-2005
 */
package it.algos.base.azione.archivio;

import it.algos.base.azione.Azione;
import it.algos.base.azione.adapter.AzAdapterAction;
import it.algos.base.errore.Errore;
import it.algos.base.menu.menu.MenuBase;
import it.algos.base.pref.Pref;

import java.awt.event.ActionEvent;

/**
 * Dialogo di preferenze.
 * <p/>
 * Questa classe azione concreta: <ul>
 * <li> Apre un dialogo di preferenze </li>
 * <li> Implementa il metodo <code>actionPerformed</code> della interfaccia
 * <code>ActionListener</code> </li>
 * <li> Viene usata nei menu, nelle toolbar e nei bottoni di comando </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 9-gen-2005 ore 8.13.59
 */
public final class AzPreferenze extends AzAdapterAction {

    /**
     * Testo della chiave per recuperare l'azione (obbligatorio)
     */
    private static final String CHIAVE = Azione.PREFERENZE;

    /**
     * Testo di default da mostrare
     * (obbligatorio per le azioni dei menu/bottoni/toolbar)
     */
    private static final String NOME = "Preferenze...";

    /**
     * Nome del disegno di default da mostrare
     * (consigliato per le azioni dei menu/bottoni/toolbar)
     */
    private static final String ICONA_PICCOLA = "Preferenze16";

    private static final String ICONA_MEDIA = "Preferenze24";

    private static final String ICONA_GRANDE = "Preferenze";

    /**
     * Descrizione di default per il tooltiptext (facoltativo)
     */
    private static final String TESTO_TIP = "Preferenze";

    /**
     * Descrizione di default per l'aiuto in linea (facoltativo)
     */
    private static final String TESTO_HELP = "Apre un dialogo di preferenze";

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
     * Costante del menu per il posizionamento dell'azione
     */
    private static final MenuBase.MenuTipo MENU_TIPO = MenuBase.MenuTipo.ARCHIVIO;

    /**
     * Costante per la tipologia di utente abilitato a questa azione
     */
    private static final Pref.Utente UTENTE = Pref.Utente.user;


    /**
     * Costruttore completo senza parametri.
     */
    public AzPreferenze() {
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
        super.setColonnaMenu(MENU_TIPO);
        super.setUtente(UTENTE);
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
        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe */
            super.getGestore().preferenze(unEvento, this);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

}// fine della classe
