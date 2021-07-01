/**
 * Title:     AzRestore
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      19-gen-2007
 */
package it.algos.base.azione.archivio;

import it.algos.base.azione.Azione;
import it.algos.base.azione.adapter.AzAdapterAction;
import it.algos.base.errore.Errore;
import it.algos.base.menu.menu.MenuBase;
import it.algos.base.pref.Pref;
import it.algos.base.progetto.Progetto;

import java.awt.event.ActionEvent;

/**
 * Azione Restore.
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
public final class AzRestore extends AzAdapterAction {

    /**
     * Testo della chiave per recuperare l'azione (obbligatorio)
     */
    private static final String CHIAVE = Azione.RESTORE;

    /**
     * Testo di default da mostrare
     * (obbligatorio per le azioni dei menu/bottoni/toolbar)
     */
    private static final String NOME = "Restore...";

    /**
     * Nome del disegno di default da mostrare
     * (consigliato per le azioni dei menu/bottoni/toolbar)
     */
    private static final String ICONA_PICCOLA = "restore16";

    private static final String ICONA_MEDIA = "restore24";

    private static final String ICONA_GRANDE = "restore24";

    /**
     * Descrizione di default per il tooltiptext (facoltativo)
     */
    private static final String TESTO_TIP = "Ripristino del backup";

    /**
     * Descrizione di default per l'aiuto in linea (facoltativo)
     */
    private static final String TESTO_HELP = "Ripristina tutti i dati da un backup";

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
    private static final Pref.Utente UTENTE = Pref.Utente.admin;


    /**
     * Costruttore completo senza parametri.
     */
    public AzRestore() {
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
        super.setChiave(AzRestore.CHIAVE);
        super.setNome(AzRestore.NOME);
        super.setIconaPiccola(AzRestore.ICONA_PICCOLA);
        super.setIconaMedia(AzRestore.ICONA_MEDIA);
        super.setIconaGrande(AzRestore.ICONA_GRANDE);
        super.setTooltip(AzRestore.TESTO_TIP);
        super.setHelp(AzRestore.TESTO_HELP);
        super.setCarattereAcceleratore(AzRestore.ACCELERATORE);
        super.setCarattereMnemonico(AzRestore.MNEMONICO);
        super.setCarattereComando(AzRestore.COMANDO);
        super.setAttiva(AzRestore.ATTIVA);
        super.setAbilitataPartenza(AzRestore.ABILITATA);
        super.setColonnaMenu(AzRestore.MENU_TIPO);
        super.setUtente(AzRestore.UTENTE);
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
            Progetto.restore();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

}// fine della classe
