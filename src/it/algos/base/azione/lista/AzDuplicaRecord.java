/**
 * Title:     AzDuplicaRecord
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      1-giu-2006
 */
package it.algos.base.azione.lista;

import it.algos.base.azione.Azione;
import it.algos.base.azione.adapter.AzAdapterAction;
import it.algos.base.costante.CostanteTesto;
import it.algos.base.errore.Errore;
import it.algos.base.menu.menu.MenuBase;
import it.algos.base.testo.Testo;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Duplica il record selezionato.
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 22-dic-2004 ore 11.55.31
 */
public final class AzDuplicaRecord extends AzAdapterAction {

    /**
     * Testo della chiave per recuperare l'azione (obbligatorio)
     */
    private static final String CHIAVE = Azione.DUPLICA_RECORD;

    /**
     * Testo di default da mostrare (obbligatorio)
     */
    private static final String NOME = Testo.get(CostanteTesto.DUPLICA_RECORD);

    /**
     * Nome del disegno di default da mostrare (consigliato)
     */
    private static final String ICONA_PICCOLA = "Duplica16";

    private static final String ICONA_MEDIA = "Duplica24";

    private static final String ICONA_GRANDE = "";

    /**
     * Descrizione di default per il tooltiptext (facoltativo)
     */
    private static final String TIP = Testo.get(CostanteTesto.DUPLICA_RECORD_TIP);

    /**
     * Descrizione di default per l'aiuto in linea (facoltativo)
     */
    private static final String HELP = Testo.get(CostanteTesto.DUPLICA_RECORD_HELP);

    /**
     * Carattere del tasto acceleratore (facoltativo)
     */
    private static final char ACCELERATORE = 'D';

    /**
     * codice carattere di default per il tasto mnemonico (facoltativo)
     */
    private static final int MNEMONICO = KeyEvent.VK_D;

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
    public AzDuplicaRecord() {
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
        super.setChiave(AzDuplicaRecord.CHIAVE);
        super.setNome(AzDuplicaRecord.NOME);
        super.setIconaPiccola(AzDuplicaRecord.ICONA_PICCOLA);
        super.setIconaMedia(AzDuplicaRecord.ICONA_MEDIA);
        super.setIconaGrande(AzDuplicaRecord.ICONA_GRANDE);
        super.setTooltip(AzDuplicaRecord.TIP);
        super.setHelp(AzDuplicaRecord.HELP);
        super.setCarattereAcceleratore(AzDuplicaRecord.ACCELERATORE);
        super.setCarattereMnemonico(AzDuplicaRecord.MNEMONICO);
        super.setCarattereComando(AzDuplicaRecord.COMANDO);
        super.setAttiva(AzDuplicaRecord.ATTIVA);
        super.setAbilitataPartenza(AzDuplicaRecord.ABILITATA);
        super.setColonnaMenu(AzDuplicaRecord.MENU_TIPO);
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
            super.getGestore().duplicaRecord(unEvento, this);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

}// fine della classe
