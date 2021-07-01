/**
 * Copyright(c): 2005
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 3-4-05
 */

package it.algos.base.azione.lista;

import it.algos.base.azione.Azione;
import it.algos.base.azione.adapter.AzAdapterAction;
import it.algos.base.errore.Errore;

import java.awt.event.ActionEvent;

/**
 * Azione SalvaSelezione.
 * <p/>
 * Questa classe azione concreta: <ul>
 * <li> Registra sul database  la selezione della lista </li>
 * <li> Implementa il metodo <code>actionPerformed</code> della interfaccia
 * <code>ActionListener</code> </li>
 * <li> Viene usata nei menu, nelle toolbar e nei bottoni di comando </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 3-4-05
 */
public final class AzSalvaSelezione extends AzAdapterAction {

    /**
     * Testo della chiave per recuperare l'azione (obbligatorio)
     */
    private static final String CHIAVE = Azione.SALVA_SELEZIONE;

    /**
     * Testo di default da mostrare
     * (obbligatorio per le azioni dei menu/bottoni/toolbar)
     */
    private static final String NOME = "Salva la selezione...";

    /**
     * Nome del disegno di default da mostrare
     * (consigliato per le azioni dei menu/bottoni/toolbar)
     */
    private static final String ICONA_PICCOLA = "16";

    private static final String ICONA_MEDIA = "24";

    private static final String ICONA_GRANDE = "";

    /**
     * Descrizione di default per il tooltiptext (facoltativo)
     */
    private static final String TESTO_TIP = "Salva la selezione";

    /**
     * Descrizione di default per l'aiuto in linea (facoltativo)
     */
    private static final String TESTO_HELP = "Salva la selezione";

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
    public AzSalvaSelezione() {
        /* rimanda al costruttore della superclasse */
        super();

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

    }


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
            super.getGestore().salvaSelezione(unEvento, this);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

} // fine della classe
