/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      24-dic-2004
 */
package it.algos.base.azione.modulo;

import it.algos.base.azione.adapter.AzAdapterAction;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pref.Pref;

import java.awt.event.ActionEvent;

/**
 * Avvio del Modulo.
 * <p/>
 * Questa classe azione concreta: <ul>
 * <li> Avvia l'esecuzione di un modulo </li>
 * <li> Implementa il metodo <code>actionPerformed</code> della interfaccia
 * <code>ActionListener</code> </li>
 * <li> Viene usata nei menu, nelle toolbar e nei bottoni di comando </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 24-dic-2004 ore 11.40.29
 */
public final class AzAvviaModulo extends AzAdapterAction {

    /**
     * Nome del disegno di default da mostrare (consigliato)
     */
    private static final String ICONA_PICCOLA = "";

    private static final String ICONA_MEDIA = "";

    private static final String ICONA_GRANDE = "";

    /**
     * Descrizione di default per il tooltiptext (facoltativo)
     */
//    private static final String TIP = "Apre il modulo ";
    private static final String TIP = "";

    /**
     * Descrizione di default per l'aiuto in linea (facoltativo)
     */
    private static final String HELP = "Apre il modulo ";

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
     * Riferimento al Modulo da avviare
     */
    private Modulo modulo = null;


    /**
     * Costruttore completo senza parametri.
     *
     * @param unModulo riferimento al Modulo da avviare
     */
    public AzAvviaModulo(Modulo unModulo) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setModulo(unModulo);

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
        super.setChiave(this.getModulo().getNomeChiave());
        super.setIconaPiccola(ICONA_PICCOLA);
        super.setIconaMedia(ICONA_MEDIA);
        super.setIconaGrande(ICONA_GRANDE);
        super.setCarattereAcceleratore(ACCELERATORE);
        super.setCarattereMnemonico(MNEMONICO);
        super.setCarattereComando(COMANDO);
        super.setAttiva(ATTIVA);
        super.setAbilitataPartenza(ABILITATA);
    }// fine del metodo inizia


    /**
     * Regolazioni delle proprieta dell'azione.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    protected void regola() {
        try { // prova ad eseguire il codice

            if (Lib.Testo.isValida(this.getModulo().getTitoloMenu())) {
                super.setNome(this.getModulo().getTitoloMenu());
            } else {
                super.setNome(this.getModulo().getNomeModulo());
            }// fine del blocco if-else

            super.setTooltip(TIP + this.getNome());
            super.setHelp(HELP + this.getNome());

            /* invoca il metodo sovrascritto della superclasse */
            super.regola();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    private Modulo getModulo() {
        return modulo;
    }


    private void setModulo(Modulo modulo) {
        this.modulo = modulo;
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
        /* variabili e costanti locali di lavoro */
        boolean menuAttivo;
        Modulo modulo;
        Navigatore nav;

        try { // prova ad eseguire il codice

            menuAttivo = Pref.GUI.abilitati.is();
            modulo = this.getModulo();

            if (!menuAttivo) {//gac
                /* disabilita questa azione */
                this.setEnabled(false);
            }// fine del blocco if

            /* se il modulo non è ancora stato avviato, lo avvia la prima volta
             * se il modulo è già stato avviato apre il navigatore corrente */
            if (!modulo.isAvviato()) {
                modulo.avvia();
            } else {
                nav = modulo.getNavigatoreCorrente();
                if (nav != null) {
                    nav.apriNavigatore();
                }// fine del blocco if
            }// fine del blocco if-else


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

}// fine della classe
