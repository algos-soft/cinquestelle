/**
 * Title:     AzPreferito
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      16-mar-2007
 */
package it.algos.base.azione.lista;

import it.algos.base.azione.Azione;
import it.algos.base.azione.adapter.AzAdapterAction;
import it.algos.base.errore.Errore;
import it.algos.base.menu.menu.MenuBase;

import java.awt.event.ActionEvent;

/**
 * Imposta il record selezionato come Preferito.
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 16-mar-2007 ore 11.55.31
 */
public final class AzPreferito extends AzAdapterAction {

    /**
     * Testo della chiave per recuperare l'azione (obbligatorio)
     */
    private static final String CHIAVE = Azione.PREFERITO;

    /**
     * Testo di default da mostrare (obbligatorio)
     */
    private static final String NOME = "Imposta come Preferito";

    /**
     * Nome del disegno di default da mostrare (consigliato)
     */
    private static final String ICONA_PICCOLA = "checkmark16";

    private static final String ICONA_MEDIA = "checkmark24";

    private static final String ICONA_GRANDE = "Preferito";

    /**
     * Descrizione di default per il tooltiptext (facoltativo)
     */
    private static final String TIP = "Imposta il record selezionato come Preferito";

    /**
     * Descrizione di default per l'aiuto in linea (facoltativo)
     */
    private static final String HELP = "Imposta il record selezionato come Preferito";

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
     * Costruttore completo senza parametri.
     */
    public AzPreferito() {
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
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* regola le variabili*/
        super.setChiave(AzPreferito.CHIAVE);
        super.setNome(AzPreferito.NOME);
        super.setIconaPiccola(AzPreferito.ICONA_PICCOLA);
        super.setIconaMedia(AzPreferito.ICONA_MEDIA);
        super.setIconaGrande(AzPreferito.ICONA_GRANDE);
        super.setTooltip(AzPreferito.TIP);
        super.setHelp(AzPreferito.HELP);
//        super.setCarattereAcceleratore(AzPreferito.ACCELERATORE);
//        super.setCarattereMnemonico(AzPreferito.MNEMONICO);
        super.setCarattereComando(AzPreferito.COMANDO);
        super.setAttiva(AzPreferito.ATTIVA);
        super.setAbilitataPartenza(AzPreferito.ABILITATA);
        super.setColonnaMenu(AzPreferito.MENU_TIPO);
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
            super.getGestore().setPreferito(unEvento, this);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

}// fine della classe
