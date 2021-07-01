/**
 * Title:        Pannello.java
 * Package:      it.algos.base.pannello
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 2.0  /
 * Creato:       il 20 agosto 2004 alle 13.25
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2004  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa interfaccia e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.pannello;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.evento.Eventi;
import it.algos.base.interfaccia.ContenitoreCampi;
import it.algos.base.layout.Layout;
import it.algos.base.wrapper.SetCampi;

import javax.swing.SwingConstants;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;

/**
 * Questa interfaccia e' responsabile di: <br>
 * A - Implementare il <i>design pattern</i> <b>Facade</b>  <br>
 * B - Fornisce un interfaccia unificata per un insieme di interfacce presenti
 * nel package. <br>
 * <br> <br>
 * Questo package comprende <ol>
 * <li> Pannello - interfaccia <br>
 * <li> PannelloBase - classe astratta <br>
 * <li> PannelloDefault - classe concreta <br>
 * </ol>
 *
 * @author Guido Andrea Ceresa
 * @author Alberto Colombo
 * @author Alessandro Valbonesi
 * @author alex
 * @version 1.0  /  20 agosto 2004 ore 13.25
 */
public interface Pannello {

    /**
     * disegna bordi e sfondi ai vari pannelli
     */
    public static final boolean DEBUG = false;

    /**
     * usa un titolo con il nome della classe
     */
    public static final boolean DEBUG_CON_TITOLO = false;


    /**
     * Aggiunge un oggetto generico al pannello.
     * <p/>
     *
     * @param oggetto oggetto/i da disporre in un pannello; puo' essere:
     *                Component - un singolo componente (Campo od altro)
     *                String - un nome set, un nome di campo singolo, una lista di nomi
     *                ArrayList - di nomi, di oggetti Campo, di componenti <br>
     */
    public abstract void add(Object oggetto);


    /**
     * Aggiunge un oggetto generico al pannello.
     * <p/>
     *
     * @param unSet di campi
     */
    public abstract void add(SetCampi unSet);


    /**
     * Abilita l'uso del gap fisso.
     * <p/>
     *
     * @param usaGapFisso flag per usare il gap fisso
     */
    public abstract void setUsaGapFisso(boolean usaGapFisso);


    /**
     * Regola il gap effettivo (fisso).
     * <p/>
     * Questo e' il gap che viene usato. <br>
     *
     * @param gap fisso
     */
    public abstract void setGapFisso(int gap);


    /**
     * Regola il gap preferito.
     * <p/>
     * Se si usa il gap fisso, questo e' il gap che viene usato. <br>
     *
     * @param gapPreferito il gap preferito (o fisso)
     */
    public abstract void setGapPreferito(int gapPreferito);


    /**
     * Regola il gap minimo.
     * <p/>
     * Significativo solo se si usa il gap variabile. <br>
     *
     * @param gapMinimo il gap minimo
     */
    public abstract void setGapMinimo(int gapMinimo);


    /**
     * Regola il gap massimo.
     * <p/>
     * Significativo solo se si usa il gap variabile. <br>
     *
     * @param gapMassimo il gap massimo
     */
    public abstract void setGapMassimo(int gapMassimo);


    /**
     * Recupera un singolo campo contenuto nel Pannello.
     * <p/>
     *
     * @param pos la posizione nell'elenco dei campi
     *            (0 per il primo)
     *
     * @return il campo recuperato
     */
    public abstract Campo getCampoPannello(int pos);


    /**
     * Recupera un singolo campo contenuto nel Pannello.
     * <p/>
     *
     * @param nome il nome interno del campo
     *
     * @return il campo recuperato
     */
    public abstract Campo getCampoPannello(String nome);


    /**
     * Ritorna l'elenco dei campi contenuti nel Pannello.
     * <p/>
     * Discende ricorsivamente i contenuti grafici alla ricerca
     * di oggetti PannelloCampo.<br>
     *
     * @return la lista dei campi contenuti nel pannello
     */
    public abstract ArrayList<Campo> getCampiPannello();


    /**
     * Allinea il bordo sinistro dei campi presenti nel pannello.
     * <p/>
     * Opera solo sui campi che hanno un'etichetta a sinistra del campo <br>
     */
    public abstract void allineaCampi();


    /**
     * Allinea il bordo sinistro dei campi presenti nel pannello.
     * <p/>
     * Opera solo sui campi che hanno un'etichetta a sinistra del campo <br>
     *
     * @param bandiera tipo di allineamento testo etichetta
     */
    public abstract void allineaCampi(Bandiera bandiera);


    /**
     * Aggiunge un componente senza modificarlo.
     * <p/>
     * Non modifica dimensioni e allineamento.<br>
     * Non aggiunge filler.<br>
     *
     * @param comp il componente da aggiungere
     */
    public abstract void addOriginale(Component comp);


    /**
     * Regola il tipo di allineamento dei componenti nel verso
     * perpendicolare a quello del layout.
     * <p/>
     *
     * @param allineamento il codice dell'allineamento
     *                     puo' essere Layout.ALLINEA_ALTO, ALLINEA_SX, ALLINEA_BASSO, ALLINEA_DX
     *
     * @see PannelloFlusso
     */
    public abstract void setAllineamento(int allineamento);


    /**
     * Considera anche i componenti invisibili
     * disegnando il contenitore.
     * <p/>
     *
     * @param flag true per considerare anche i componenti invisibili
     */
    public abstract void setConsideraComponentiInvisibili(boolean flag);

//    /**
//     * Ritorna lo stato del flag di dimensionamento esterno.
//     * <p/>
//     *
//     * @return true se il dimensionamento estermo e' attivato (altrimenti e' interno)
//     */
//    public abstract boolean isDimensionamentoEsterno();


    /**
     * Ritorna i componenti effettivi di questo pannello.
     * <p/>
     * Ignora i componenti fittizi usati per il dimensionamento
     * (fillers, etc.) <br>
     *
     * @return i componenti effettivi.
     */
    public abstract Component[] getComponentiEffettivi();


    /**
     * Ritorna la dimensione preferita del pannello.
     * <p/>
     *
     * @return la dimensione preferita
     */
    public abstract Dimension getPreferredSize();


    /**
     * Ritorna la dimensione minima del pannello.
     * <p/>
     *
     * @return la dimensione preferita
     */
    public abstract Dimension getMinimumSize();


    /**
     * Ritorna la dimensione massima del pannello.
     * <p/>
     *
     * @return la dimensione preferita
     */
    public abstract Dimension getMaximumSize();


    /**
     * Regola l'opacita' del pannelllo.
     * <p/>
     *
     * @param flag true opaco, false trasparente
     */
    public abstract void setOpaque(boolean flag);


    /**
     * Regola il colore di sfondo del pannello.
     * <p/>
     *
     * @param colore di sfondo
     */
    public abstract void setBackground(Color colore);


    /**
     * Regola il colore di primo piano pannello.
     * <p/>
     *
     * @param colore di primo piano
     */
    public abstract void setForeground(Color colore);


    /**
     * Regola la dimensione preferita del pannello.
     * <p/>
     *
     * @param w la larghezza
     * @param h l'altezza
     */
    public abstract void setPreferredSize(int w, int h);


    /**
     * Regola la dimensione minima del pannello.
     * <p/>
     *
     * @param w la larghezza
     * @param h l'altezza
     */
    public void setMinimumSize(int w, int h);


    /**
     * Regola la dimensione massima del pannello.
     * <p/>
     *
     * @param w la larghezza
     * @param h l'altezza
     */
    public void setMaximumSize(int w, int h);


    /**
     * Regola la larghezza preferita del pannello.
     * <p/>
     * Mantiene l'altezza preferita corrente.<br>
     * (se non era specificata, la pone pari a zero)<br>
     *
     * @param w il valore da assegnare alla larghezza preferita
     */
    public abstract void setPreferredWidth(int w);


    /**
     * Regola l'altezza preferita del pannello.
     * <p/>
     * Mantiene la larghezza preferita corrente.<br>
     * (se non era specificata, la pone pari a zero)<br>
     *
     * @param h il valore da assegnare all'altezza preferita
     */
    public abstract void setPreferredHeigth(int h);


    /**
     * Regola la larghezza minima del pannello.
     * <p/>
     * Mantiene l'altezza minima corrente.<br>
     * (se non era specificata, la pone pari a zero)<br>
     *
     * @param w il valore da assegnare alla larghezza minima
     */
    public abstract void setMinimumWidth(int w);


    /**
     * Regola l'altezza mimina del pannello.
     * <p/>
     * Mantiene la larghezza mimina corrente.<br>
     * (se non era specificata, la pone pari a zero)<br>
     *
     * @param h il valore da assegnare all'altezza mimina
     */
    public abstract void setMinimumHeigth(int h);


    /**
     * Regola la larghezza massima di un componente.
     * <p/>
     * Mantiene l'altezza massima corrente.<br>
     * (se non era specificata, la pone pari a infinito)<br>
     *
     * @param w il valore da assegnare alla larghezza massima
     */
    public abstract void setMaximumWidth(int w);


    /**
     * Regola l'altezza massima di un componente.
     * <p/>
     * Mantiene la larghezza massima corrente.<br>
     * (se non era specificata, la pone pari a infinito)<br>
     *
     * @param h il valore da assegnare all'altezza massima
     */
    public abstract void setMaximumHeigth(int h);


    /**
     * Blocca la larghezza massima di questo componente.
     * <p/>
     * Pone la larghezza massima pari a quella preferita.
     */
    public abstract void bloccaLarMax();


    /**
     * Blocca la larghezza minima di questo componente.
     * <p/>
     * Pone la larghezza minima pari a quella preferita.
     */
    public abstract void bloccaLarMin();


    /**
     * Blocca la larghezza di questo componente.
     * <p/>
     * Pone la larghezza minima e massima pari a quella preferita.
     */
    public abstract void bloccaLarghezza();


    /**
     * Blocca l'altezza massima di questo componente.
     * <p/>
     * Pone l'altezza massima pari a quella preferita.
     */
    public abstract void bloccaAltMax();


    /**
     * Blocca l'altezza minima di questo componente.
     * <p/>
     * Pone l'altezza minima pari a quella preferita.
     */
    public abstract void bloccaAltMin();


    /**
     * Blocca l'altezza di questo componente.
     * <p/>
     * Pone l'altezza minima e massima pari a quella preferita.
     */
    public abstract void bloccaAltezza();


    /**
     * Blocca dimensione massima di questo componente.
     * <p/>
     * Pone la dimensione massima pari alla preferredSize.
     */
    public abstract void bloccaDimMax();


    /**
     * Blocca dimensione minima di questo componente.
     * <p/>
     * Pone la dimensione minima pari alla preferredSize.
     */
    public abstract void bloccaDimMin();


    /**
     * Blocca dimensione questo componente.
     * <p/>
     * Pone le dimensioni minima e massima pari alla preferredSize.
     */
    public abstract void bloccaDim();


    /**
     * Sblocca la larghezza massima di questo componente.
     * <p/>
     * Pone la larghezza massima pari a infinito.
     */
    public abstract void sbloccaLarMax();


    /**
     * Sblocca la larghezza minima di questo componente.
     * <p/>
     * Pone la larghezza minima pari a zero.
     */
    public abstract void sbloccaLarMin();


    /**
     * Sblocca la larghezza di questo componente.
     * <p/>
     * Pone la larghezza minima a zero e massima a infinito.
     */
    public abstract void sbloccaLarghezza();


    /**
     * Sblocca l'altezza massima di questo componente.
     * <p/>
     * Pone la altezza massima pari a infinito.
     */
    public abstract void sbloccaAltMax();


    /**
     * Sblocca l'altezza minima di questo componente.
     * <p/>
     * Pone l'altezza minima pari a zero.
     */
    public abstract void sbloccaAltMin();


    /**
     * Sblocca l'altezza di questo componente.
     * <p/>
     * Pone l'altezza minima a zero e massima a infinito.
     */
    public abstract void sbloccaAltezza();


    /**
     * Sblocca dimensione massima in larghezza e in altezza di questo componente.
     * <p/>
     * Pone la larghezza massima a infinito e l'altezza massima a infinito.
     */
    public abstract void sbloccaDimMax();


    /**
     * Sblocca dimensione minima in larghezza e in altezza di questo componente.
     * <p/>
     * Pone la larghezza minima a zero e l'altezza minima a zero.
     */
    public abstract void sbloccaDimMin();


    /**
     * Sblocca dimensione massima in larghezza e in altezza di questo componente.
     * <p/>
     * Pone la dimensione minima a zero e la massima a infinito.
     */
    public abstract void sbloccaDim();


    /**
     * Fissa la dimensione di questo pannello a una misura data.
     * <p/>
     * Regola le variabili preferredSize, minimumSize e maximumSize
     * tutte al valore dato. <br>
     *
     * @param lar larghezza
     * @param alt larghezza
     */
    public abstract void setDimFissa(int lar, int alt);


    /**
     * Crea un bordo con margini interni e titolo.
     * <p/>
     *
     * @return il bordo creato
     */
    public abstract Border creaBordo(int top, int left, int bottom, int right, String titolo);


    /**
     * Crea un bordo con margine interno e titolo.
     * <p/>
     *
     * @param margine valido per tutti i lati
     * @param titolo  eventuale del bordo
     *
     * @return il bordo creato
     */
    public abstract Border creaBordo(int margine, String titolo);


    /**
     * Crea un bordo con margini interni senza titolo.
     * <p/>
     *
     * @param top    margine
     * @param left   margine
     * @param bottom margine
     * @param right  margine
     *
     * @return il bordo creato
     */
    public abstract Border creaBordo(int top, int left, int bottom, int right);


    /**
     * Crea un bordo con margine interno senza titolo.
     * <p/>
     *
     * @param margine valido per tutti i lati
     *
     * @return il bordo creato
     */
    public abstract Border creaBordo(int margine);


    /**
     * Crea un bordo con titolo e margine interno di default di 10 pixel.
     * <p/>
     *
     * @param titolo del bordo
     *
     * @return il bordo creato
     */
    public abstract Border creaBordo(String titolo);


    /**
     * Crea un bordo senza titolo e margine interno di default di 10 pixel.
     * <p/>
     *
     * @return il bordo creato
     */
    public abstract Border creaBordo();


    /**
     * Ritorna questo pannello come oggetto di questa classe.
     * <p/>
     *
     * @return questo pannello
     */
    public abstract PannelloBase getPanFisso();


    public abstract Layout getLayoutAlgos();


    /**
     * Avvisa tutti i listener.
     * <p/>
     * Avvisa tutti i listener che si sono registrati
     * per questo tipo di evento <br>
     * L'evento viene creato al momento <br>
     * È responsabilità della classe invocare questo metodo quando
     * si creano le condizioni per generare l'evento <br>
     *
     * @see javax.swing.event.EventListenerList
     */
    public abstract void fire(Eventi unEvento);


    /**
     * Avvisa tutti i listener dell'evento Pannello Modificato.
     */
    public abstract void fireModificato();


    /**
     * Assegna il riferimento a un contenitore di campi.
     * <p/>
     *
     * @param cont il contenitore di campi di riferimento
     */
    public abstract void setContenitoreCampi(ContenitoreCampi cont);


    /**
     * Attiva o disattiva il ridimensionamento dei componenti
     * in entrambi i versi del layout.
     * <p/>
     * Se il ridimensionamento e' attivo ridimensiona i componenti rispettando
     * la dimensione preferita, minima e massima. <br>
     * Se il ridimensionamento non e' attivo visualizza i componenti alla
     * loro dimensione preferita. <br>
     * Equivale ad effettuare le due chiamate setRidimensionaParallelo
     * e setRidimensionaPerpendicolare usando lo stesso parametro.
     *
     * @param flag per attivare o disattivare il ridimensionamento
     */
    public abstract void setRidimensionaComponenti(boolean flag);


    /**
     * Attiva o disattiva il ridimensionamento dei componenti
     * nel verso parallelo al layout.
     * <p/>
     * Se il ridimensionamento e' attivo ridimensiona i componenti rispettando
     * la dimensione preferita, minima e massima. <br>
     * Se il ridimensionamento non e' attivo visualizza i componenti alla
     * loro dimensione preferita. <br>
     *
     * @param flag per attivare o disattivare il ridimensionamento parallelo
     */
    public abstract void setRidimensionaParallelo(boolean flag);


    /**
     * Attiva o disattiva il ridimensionamento dei componenti
     * nel verso perpendicolare al layout.
     * <p/>
     * Se il ridimensionamento e' attivo ridimensiona i componenti rispettando
     * la dimensione preferita, minima e massima. <br>
     * Se il ridimensionamento non e' attivo visualizza i componenti alla
     * loro dimensione preferita. <br>
     *
     * @param flag per attivare o disattivare il ridimensionamento perpendicolare
     */
    public abstract void setRidimensionaPerpendicolare(boolean flag);


    /**
     * Abilita l'uso dello scorrevole.
     * <p/>
     *
     * @param flag true per abilitare l'uso dello scorrevole, false per disabilitarlo
     */
    public abstract void setUsaScorrevole(boolean flag);


    /**
     * Attiva o disattiva l'uso del bordo nello scorrevole.
     * <p/>
     *
     * @param flag per attivare/disattivare l'uso del bordo.
     */
    public abstract void setScorrevoleBordato(boolean flag);


    /**
     * Rimuove tutti i componenti contenuti nel pannello.
     * <p/>
     */
    public abstract void removeAll();


    /**
     * Attiva il debug del pannello.
     * <p/>
     */
    public abstract void debug();


    /**
     * Attiva il debug del pannello.
     * <p/>
     * Il pannello diventa:
     * - sfondo grigio opaco
     * - bordato con nome classe
     *
     * @param usaTitolo true per usare il nome classe come titolo
     */
    public abstract void debug(boolean usaTitolo);

    /**
     * Classe interna Enumerazione.
     * Codifica dei tipi di allineamento etichetta dei campi
     */
    public enum Bandiera {

        sinistra(SwingConstants.LEFT),
        destra(SwingConstants.RIGHT);

        /**
         * costante codificata da Swing
         */
        private int costante;


        /**
         * Costruttore completo con parametri.
         *
         * @param costante codificata da Swing
         */
        Bandiera(int costante) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setCostante(costante);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public int getCostante() {
            return costante;
        }


        private void setCostante(int costante) {
            this.costante = costante;
        }
    }// fine della classe

}// fine della