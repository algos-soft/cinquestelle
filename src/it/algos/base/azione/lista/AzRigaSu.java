/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      22-dic-2004
 */
package it.algos.base.azione.lista;

import it.algos.base.azione.Azione;
import it.algos.base.azione.AzioneBase;
import it.algos.base.errore.Errore;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Sposta in alto il record.
 * <p/>
 * Questa classe azione concreta: <ul>
 * <li> @TODO DESCRIZIONE SINTETICA DELL'AZIONE </li>
 * <li> Implementa il metodo <code>actionPerformed</code> della interfaccia
 * <code>ActionListener</code> </li>
 * <li> Viene usata nei menu, nelle toolbar e nei bottoni di comando </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 22-dic-2004 ore 12.21.26
 */
public final class AzRigaSu extends AzioneBase {

    /**
     * Testo della chiave per recuperare l'azione (obbligatorio)
     */
    private static final String CHIAVE = Azione.RIGA_SU;

    /**
     * Testo di default da mostrare (obbligatorio)
     */
    private static final String NOME = "Sposta in alto la riga";

    /**
     * Nome del disegno di default da mostrare (consigliato)
     */
    private static final String ICONA_PICCOLA = "listaspostasu16";

    private static final String ICONA_MEDIA = "listaspostasu24";

    private static final String ICONA_GRANDE = "listaspostasu24";

    /**
     * Descrizione di default per il tooltiptext (facoltativo)
     */
    private static final String TIP = "Sposta in alto la riga";

    /**
     * Descrizione di default per l'aiuto in linea (facoltativo)
     */
    private static final String HELP = "Sposta verso l'alto la riga selezionata";

    /**
     * Carattere del tasto acceleratore (facoltativo)
     */
    private static final char ACCELERATORE = ' ';

    /**
     * codice carattere di default per il tasto mnemonico (facoltativo)
     */
    private static final int MNEMONICO = KeyEvent.VK_UP;

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
    public AzRigaSu() {
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
            super.getGestore().rigaSu(unEvento, this);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } // fine del metodo

}// fine della classe
