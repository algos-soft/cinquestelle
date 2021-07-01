/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      22-dic-2004
 */
package it.algos.base.azione.record;

import it.algos.base.azione.Azione;
import it.algos.base.azione.adapter.AzAdapterAction;
import it.algos.base.costante.CostanteTesto;
import it.algos.base.errore.Errore;
import it.algos.base.testo.Testo;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Spostamento al record successivo.
 * <p/>
 * Questa classe azione concreta: <ul>
 * <li> Carica nella scheda il record successivo della lista </li>
 * <li> Implementa il metodo <code>actionPerformed</code> della interfaccia
 * <code>ActionListener</code> </li>
 * <li> Viene usata nei menu, nelle toolbar e nei bottoni di comando </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 22-dic-2004 ore 12.18.15
 */
public final class AzRecordSuccessivo extends AzAdapterAction {

    /**
     * Testo della chiave per recuperare l'azione (obbligatorio)
     */
    private static final String CHIAVE = Azione.RECORD_SUCCESSIVO;

    /**
     * Testo di default da mostrare (obbligatorio)
     */
    private static final String NOME = Testo.get(CostanteTesto.RECORD_SUCCESSIVO);

    /**
     * Nome del disegno di default da mostrare (consigliato)
     */
    private static final String ICONA_PICCOLA = "Successivo16";

    private static final String ICONA_MEDIA = "Successivo24";

    private static final String ICONA_GRANDE = "Successivo";

    /**
     * Descrizione di default per il tooltiptext (facoltativo)
     */
    private static final String TIP = Testo.get(CostanteTesto.RECORD_SUCCESSIVO_TIP);

    /**
     * Descrizione di default per l'aiuto in linea (facoltativo)
     */
    private static final String HELP = Testo.get(CostanteTesto.TESTO_RECORD_SUCCESSIVO_HELP);

    /**
     * Carattere del tasto acceleratore (facoltativo)
     */
    private static final char ACCELERATORE = ' ';

    /**
     * codice carattere di default per il tasto mnemonico (facoltativo)
     */
    private static final int MNEMONICO = KeyEvent.VK_RIGHT;

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
    private static final boolean ABILITATA = false;


    /**
     * Costruttore completo senza parametri.
     */
    public AzRecordSuccessivo() {
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
        super.setChiave(CHIAVE);
        super.setNome(NOME);
        super.setIconaPiccola(ICONA_PICCOLA);
        super.setIconaMedia(ICONA_MEDIA);
        super.setIconaGrande(ICONA_GRANDE);
        super.setTooltip(TIP);
        super.setHelp(HELP);
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
        try { // prova ad eseguire il codice
            /* invoca il metodo delegato della classe gestione eventi */
            super.getGestore().recordSuccessivo(unEvento, this);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } // fine del metodo

}// fine della classe
