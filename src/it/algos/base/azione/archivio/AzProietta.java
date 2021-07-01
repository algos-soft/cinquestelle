/**
 * Title:     AzProietta
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      21-set-2006
 */
package it.algos.base.azione.archivio;

import it.algos.base.azione.Azione;
import it.algos.base.azione.adapter.AzAdapterAction;
import it.algos.base.errore.Errore;
import it.algos.base.menu.menu.MenuBase;

import java.awt.event.ActionEvent;

/**
 * Dialogo di ricerca.
 * <p/>
 * Questa classe azione concreta: <ul>
 * <li> Apre un dialogo di ricerca </li>
 * <li> Implementa il metodo <code>actionPerformed</code> della interfaccia
 * <code>ActionListener</code> </li>
 * <li> Viene usata nei menu, nelle toolbar e nei bottoni di comando </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 9-gen-2005 ore 8.15.52
 */
public final class AzProietta extends AzAdapterAction {

    /**
     * Testo della chiave per recuperare l'azione (obbligatorio)
     */
    private static final String CHIAVE = Azione.PROIETTA;

    /**
     * Testo di default da mostrare
     * (obbligatorio per le azioni dei menu/bottoni/toolbar)
     */
    private static final String NOME = "Proietta...";

    /**
     * Nome del disegno di default da mostrare
     * (consigliato per le azioni dei menu/bottoni/toolbar)
     */
    private static final String ICONA_PICCOLA = "proietta16";

    private static final String ICONA_MEDIA = "proietta24";

    private static final String ICONA_GRANDE = "";

    /**
     * Descrizione di default per il tooltiptext (facoltativo)
     */
    private static final String TESTO_TIP = "Proietta";

    /**
     * Descrizione di default per l'aiuto in linea (facoltativo)
     */
    private static final String TESTO_HELP = "Esegue una proiezione dati";

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
    private static final MenuBase.MenuTipo MENU_TIPO = MenuBase.MenuTipo.COMPOSIZIONE;


    /**
     * Costruttore completo senza parametri.
     */
    public AzProietta() {
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
        super.setChiave(AzProietta.CHIAVE);
        super.setNome(AzProietta.NOME);
        super.setIconaPiccola(AzProietta.ICONA_PICCOLA);
        super.setIconaMedia(AzProietta.ICONA_MEDIA);
        super.setIconaGrande(AzProietta.ICONA_GRANDE);
        super.setTooltip(AzProietta.TESTO_TIP);
        super.setHelp(AzProietta.TESTO_HELP);
        super.setCarattereMnemonico(AzProietta.MNEMONICO);
        super.setCarattereComando(AzProietta.COMANDO);
        super.setAttiva(AzProietta.ATTIVA);
        super.setAbilitataPartenza(AzProietta.ABILITATA);
        super.setColonnaMenu(AzProietta.MENU_TIPO);
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
            /* invoca il metodo delegato della classe gestione eventi */
            super.getGestore().proietta(unEvento, this);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

}// fine della classe
