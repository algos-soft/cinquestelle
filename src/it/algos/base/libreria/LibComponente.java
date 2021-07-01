/**
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      20-giu-2005
 */
package it.algos.base.libreria;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.pannello.PannelloCampo;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.util.ArrayList;

/**
 * Repository di funzionalità per i Componenti.
 * <p/>
 * Tutti i metodi sono statici <br> I metodi non hanno modificatore così sono visibili all'esterno
 * del package solo utilizzando l'interfaccia unificata <b>Lib</b><br>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 20-giu-2005 ore 9.41.24
 */
public abstract class LibComponente {

    /**
     * Blocca la larghezza massima del componente.
     * <p/>
     * Pone la larghezza massima pari a quella preferita <br>
     *
     * @param comp componente su cui operare
     */
    static void bloccaLarMax(JComponent comp) {
        /* variabili e costanti locali di lavoro */
        int wPref;
        Dimension dimMax;
        int hMax;

        try { // prova ad eseguire il codice

            wPref = comp.getPreferredSize().width;

            /* se c'era gia' una altezza massima la mantiene,
             * se no la pone a infinito */
            dimMax = comp.getMaximumSize();
            if (dimMax != null) {
                hMax = dimMax.height;
            } else {
                hMax = Short.MAX_VALUE;
            }// fine del blocco if-else

            dimMax = new Dimension(wPref, hMax);
            comp.setMaximumSize(dimMax);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Blocca la larghezza minima del componente.
     * <p/>
     * Pone la larghezza minima pari a quella preferita <br>
     *
     * @param comp componente su cui operare
     */
    static void bloccaLarMin(JComponent comp) {
        /* variabili e costanti locali di lavoro */
        int wPref;
        Dimension dimMin;
        int hMin;

        try { // prova ad eseguire il codice

            wPref = comp.getPreferredSize().width;

            /* se c'era gia' una altezza minima la mantiene,
             * se no la pone a zero */
            dimMin = comp.getMinimumSize();
            if (dimMin != null) {
                hMin = dimMin.height;
            } else {
                hMin = 0;
            }// fine del blocco if-else

            dimMin = new Dimension(wPref, hMin);
            comp.setMinimumSize(dimMin);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Blocca la larghezza del componente.
     * <p/>
     * Pone la larghezza minima e massima pari a quella preferita <br>
     *
     * @param comp componente su cui operare
     */
    static void bloccaLarghezza(JComponent comp) {
        /* invoca il metodo delegato della classe */
        LibComponente.bloccaLarMin(comp);
        LibComponente.bloccaLarMax(comp);
    }


    /**
     * Blocca l'altezza massima del componente.
     * <p/>
     * Pone l'altezza massima pari a quella preferita <br>
     *
     * @param comp componente su cui operare
     */
    static void bloccaAltMax(JComponent comp) {
        /* variabili e costanti locali di lavoro */
        int hPref;
        Dimension dimMax;
        int wMax;

        try { // prova ad eseguire il codice

            hPref = comp.getPreferredSize().height;

            /* se c'era gia' una larghezza massima la mantiene,
             * se no la pone a infinito */
            dimMax = comp.getMaximumSize();
            if (dimMax != null) {
                wMax = dimMax.width;
            } else {
                wMax = Short.MAX_VALUE;
            }// fine del blocco if-else

            dimMax = new Dimension(wMax, hPref);
            comp.setMaximumSize(dimMax);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Blocca l'altezza minima del componente.
     * <p/>
     * Pone l'altezza minima pari a quella preferita <br>
     *
     * @param comp componente su cui operare
     */
    static void bloccaAltMin(JComponent comp) {
        /* variabili e costanti locali di lavoro */
        int hPref;
        Dimension dimMin;
        int wMin;

        try { // prova ad eseguire il codice

            hPref = comp.getPreferredSize().height;

            /* se c'era gia' una larghezza minima la mantiene,
             * se no la pone a zero */
            dimMin = comp.getMinimumSize();
            if (dimMin != null) {
                wMin = dimMin.width;
            } else {
                wMin = 0;
            }// fine del blocco if-else

            dimMin = new Dimension(hPref, wMin);
            comp.setMinimumSize(dimMin);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Blocca l'altezza del componente.
     * <p/>
     * Pone l'altezza minima e massima pari a quella preferita <br>
     *
     * @param comp componente su cui operare
     */
    static void bloccaAltezza(JComponent comp) {
        /* invoca il metodo delegato della classe */
        LibComponente.bloccaAltMin(comp);
        LibComponente.bloccaAltMax(comp);
    }


    /**
     * Blocca la dimensione massima del componente.
     * <p/>
     * Pone la dimensione massima pari alla preferredSize <br>
     *
     * @param comp componente su cui operare
     */
    static void bloccaDimMax(JComponent comp) {
        try { // prova ad eseguire il codice
            /* invalida il componente in modo che ritorni
             * la preferred size corretta */
            comp.invalidate();

            /* pone la maximum size pari alla preferred size */
            comp.setMaximumSize(comp.getPreferredSize());
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Blocca la dimensione minima del componente.
     * <p/>
     * Pone la dimensione minima pari alla preferredSize <br>
     *
     * @param comp componente su cui operare
     */
    static void bloccaDimMin(JComponent comp) {
        try { // prova ad eseguire il codice
            /* invalida il componente in modo che ritorni
             * la preferred size corretta */
            comp.invalidate();

            /* pone la minimum size pari alla preferred size */
            comp.setMinimumSize(comp.getPreferredSize());
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Blocca la dimensione del componente.
     * <p/>
     * Pone le dimensioni minima e massima pari alla preferredSize <br>
     *
     * @param comp componente su cui operare
     */
    public static void bloccaDim(JComponent comp) {
        /* invoca il metodo delegato della classe */
        LibComponente.bloccaDimMin(comp);
        LibComponente.bloccaDimMax(comp);
    }


    /**
     * Sblocca la larghezza massima del componente.
     * <p/>
     * Pone la larghezza massima pari a infinito <br>
     *
     * @param comp componente su cui operare
     */
    static void sbloccaLarMax(JComponent comp) {
        /* variabili e costanti locali di lavoro */
        int hMax;
        Dimension dimMax;

        try { // prova ad eseguire il codice

            /* se c'era gia' una altezza massima la mantiene,
             * se no la pone a infinito */
            dimMax = comp.getMaximumSize();
            if (dimMax != null) {
                hMax = dimMax.height;
            } else {
                hMax = Short.MAX_VALUE;
            }// fine del blocco if-else

            dimMax = new Dimension(Short.MAX_VALUE, hMax);
            comp.setMaximumSize(dimMax);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Sblocca la larghezza minima del componente.
     * <p/>
     * Pone la larghezza minima pari a zero <br>
     *
     * @param comp componente su cui operare
     */
    static void sbloccaLarMin(JComponent comp) {
        /* variabili e costanti locali di lavoro */
        Dimension dimMin;
        int hMin;

        try { // prova ad eseguire il codice

            /* se c'era gia' una altezza minima la mantiene,
             * se no la pone a infinito */
            dimMin = comp.getMinimumSize();
            if (dimMin != null) {
                hMin = dimMin.height;
            } else {
                hMin = Short.MAX_VALUE;
            }// fine del blocco if-else

            dimMin = new Dimension(0, hMin);
            comp.setMinimumSize(dimMin);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Sblocca la larghezza del componente.
     * <p/>
     * Pone la larghezza minima a zero e massima a infinito <br>
     *
     * @param comp componente su cui operare
     */
    static void sbloccaLarghezza(JComponent comp) {
        /* invoca il metodo delegato della classe */
        LibComponente.sbloccaLarMin(comp);
        LibComponente.sbloccaLarMax(comp);
    }


    /**
     * Sblocca l'altezza massima del componente.
     * <p/>
     * Pone la altezza massima pari a infinito <br>
     *
     * @param comp componente su cui operare
     */
    static void sbloccaAltMax(JComponent comp) {
        /* variabili e costanti locali di lavoro */
        int wMax;
        Dimension dimMax;

        try { // prova ad eseguire il codice

            /* se c'era gia' una larghezza massima la mantiene,
             * se no la pone a infinito */
            dimMax = comp.getMaximumSize();
            if (dimMax != null) {
                wMax = dimMax.width;
            } else {
                wMax = Short.MAX_VALUE;
            }// fine del blocco if-else

            dimMax = new Dimension(wMax, Short.MAX_VALUE);
            comp.setMaximumSize(dimMax);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Sblocca l'altezza minima del componente.
     * <p/>
     * Pone l'altezza minima pari a zero <br>
     *
     * @param comp componente su cui operare
     */
    static void sbloccaAltMin(JComponent comp) {
        /* variabili e costanti locali di lavoro */
        Dimension dimMin;
        int wMin;

        try { // prova ad eseguire il codice

            /* se c'era gia' una larghezza minima la mantiene,
             * se no la pone a zero */
            dimMin = comp.getMinimumSize();
            if (dimMin != null) {
                wMin = dimMin.width;
            } else {
                wMin = 0;
            }// fine del blocco if-else

            dimMin = new Dimension(wMin, 0);
            comp.setMinimumSize(dimMin);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Sblocca l'altezza del componente.
     * <p/>
     * Pone l'altezza minima a zero e massima a infinito <br>
     *
     * @param comp componente su cui operare
     */
    static void sbloccaAltezza(JComponent comp) {
        /* invoca il metodo delegato della classe */
        LibComponente.sbloccaAltMin(comp);
        LibComponente.sbloccaAltMax(comp);
    }


    /**
     * Sblocca dimensione massima in larghezza e in altezza del componente.
     * <p/>
     * Pone la larghezza massima a infinito e l'altezza massima a infinito <br>
     *
     * @param comp componente su cui operare
     */
    static void sbloccaDimMax(JComponent comp) {
        /* invoca il metodo delegato della classe */
        LibComponente.sbloccaLarMax(comp);
        LibComponente.sbloccaAltMax(comp);
    }


    /**
     * Sblocca dimensione minima in larghezza e in altezza del componente.
     * <p/>
     * Pone la larghezza minima a zero e l'altezza minima a zero <br>
     *
     * @param comp componente su cui operare
     */
    static void sbloccaDimMin(JComponent comp) {
        /* invoca il metodo delegato della classe */
        LibComponente.sbloccaLarMin(comp);
        LibComponente.sbloccaAltMin(comp);
    }


    /**
     * Sblocca dimensione massima in larghezza e in altezza del componente.
     * <p/>
     * Pone la dimensione minima a zero e la massima a infinito <br>
     *
     * @param comp componente su cui operare
     */
    static void sbloccaDim(JComponent comp) {
        /* invoca il metodo delegato della classe */
        LibComponente.sbloccaDimMin(comp);
        LibComponente.sbloccaDimMax(comp);
    }


    /**
     * Regola la larghezza preferita del componente.
     * <p/>
     * Mantiene l'altezza preferita corrente.<br> (se non era specificata, la pone pari a zero)<br>
     *
     * @param comp componente su cui operare
     * @param w valore da assegnare alla larghezza preferita
     */
    static void setPreferredWidth(JComponent comp, int w) {
        /* variabili e costanti locali di lavoro */
        Dimension dim;
        Dimension dimNew;
        int currH = 0;

        try { // prova ad eseguire il codice
            dim = comp.getPreferredSize();
            if (dim != null) {
                currH = dim.height;
            }// fine del blocco if-else
            dimNew = new Dimension(w, currH);
            comp.setPreferredSize(dimNew);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola l'altezza preferita del componente.
     * <p/>
     * Mantiene la larghezza preferita corrente.<br> (se non era specificata, la pone pari a
     * zero)<br>
     *
     * @param comp componente su cui operare
     * @param h valore da assegnare all'altezza preferita
     */
    static void setPreferredHeigth(JComponent comp, int h) {
        /* variabili e costanti locali di lavoro */
        Dimension dim;
        Dimension dimNew;
        int currW = 0;

        try { // prova ad eseguire il codice
            dim = comp.getPreferredSize();
            if (dim != null) {
                currW = dim.width;
            }// fine del blocco if-else
            dimNew = new Dimension(currW, h);
            comp.setPreferredSize(dimNew);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola la larghezza minima del componente.
     * <p/>
     * Mantiene l'altezza minima corrente.<br> (se non era specificata, la pone pari a zero)<br>
     *
     * @param comp componente su cui operare
     * @param w valore da assegnare alla larghezza minima
     */
    static void setMinimumWidth(JComponent comp, int w) {
        /* variabili e costanti locali di lavoro */
        Dimension dim;
        Dimension dimNew;
        int currH = 0;

        try { // prova ad eseguire il codice
            dim = comp.getMinimumSize();
            if (dim != null) {
                currH = dim.height;
            }// fine del blocco if-else
            dimNew = new Dimension(w, currH);
            comp.setMinimumSize(dimNew);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola l'altezza mimina del componente.
     * <p/>
     * Mantiene la larghezza mimina corrente.<br> (se non era specificata, la pone pari a zero)<br>
     *
     * @param comp componente su cui operare
     * @param h valore da assegnare all'altezza mimina
     */
    static void setMinimumHeigth(JComponent comp, int h) {
        /* variabili e costanti locali di lavoro */
        Dimension dim;
        Dimension dimNew;
        int currW = 0;

        try { // prova ad eseguire il codice
            dim = comp.getMinimumSize();
            if (dim != null) {
                currW = dim.width;
            }// fine del blocco if-else
            dimNew = new Dimension(currW, h);
            comp.setMinimumSize(dimNew);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola la larghezza massima del componente.
     * <p/>
     * Mantiene l'altezza massima corrente.<br>
     * (se non era specificata, la pone pari a infinito)<br>
     *
     * @param comp componente su cui operare
     * @param w valore da assegnare alla larghezza massima
     */
    static void setMaximumWidth(JComponent comp, int w) {
        /* variabili e costanti locali di lavoro */
        Dimension dim;
        Dimension dimNew;
        int currH = Integer.MAX_VALUE;

        try {    // prova ad eseguire il codice
            dim = comp.getMaximumSize();
            if (dim != null) {
                currH = dim.height;
            }// fine del blocco if-else
            dimNew = new Dimension(w, currH);
            comp.setMaximumSize(dimNew);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola l'altezza massima del componente.
     * <p/>
     * Mantiene la larghezza massima corrente.<br> (se non era specificata, la pone pari a
     * infinito)<br>
     *
     * @param comp componente su cui operare
     * @param h valore da assegnare all'altezza massima
     */
    static void setMaximumHeigth(JComponent comp, int h) {
        /* variabili e costanti locali di lavoro */
        Dimension dim;
        Dimension dimNew;
        int currW = Integer.MAX_VALUE;

        try {    // prova ad eseguire il codice
            dim = comp.getMaximumSize();
            if (dim != null) {
                currW = dim.width;
            }// fine del blocco if-else
            dimNew = new Dimension(currW, h);
            comp.setMaximumSize(dimNew);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Estrae i campi da una gerarchia di contenimento.
     * <p/>
     * Analizza ricorsivamente la gerarchia dei contenuti ed estrae i soli oggetti Campo <br>
     * L'ordine e' quello della gerarchia di contenimento <br>
     *
     * @param contenitore dal quale estrarre i campi
     *
     * @return la lista dei campi presenti nella gerarchia di contenimento del contenitore dato.
     */
    static ArrayList<Campo> estraeCampi(Container contenitore) {
        /* variabili e costanti locali di lavoro */
        ArrayList<Campo> campi = null;
        ArrayList<Campo> campiInterni;
        PannelloCampo panCampo;
        Campo campo;
        Container contInterno;

        try { // prova ad eseguire il codice
            campi = new ArrayList<Campo>();

            /* traverso tutta la collezione */
            for (Component comp : contenitore.getComponents()) {
                if (comp instanceof PannelloCampo) {
                    panCampo = (PannelloCampo)comp;
                    campo = panCampo.getCampo();
                    campi.add(campo);
                } else {
                    if (comp instanceof Container) {
                        contInterno = (Container)comp;
                        campiInterni = LibComponente.estraeCampi(contInterno);
                        campi.addAll(campiInterni);
                    }// fine del blocco if
                }// fine del blocco if-else
            } // fine del ciclo for-each

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campi;
    }


    /**
     * Risale una gerarchia di contenimento fino all'oggetto della classe richiesta.
     * <p/>
     * Se non trova il contenitore della classe richiesta ritorna null <br>
     *
     * @param comp il componente del quale risalire la gerarchia
     * @param classe la classe dell'oggetto da cercare
     *
     * @return il contenitore della classe richiesta contenente il componente
     */
    static Component risali(Component comp, Class classe) {
        /* variabili e costanti locali di lavoro */
        Component trovato = null;
        Component componente;
        boolean fine = false;

        try {    // prova ad eseguire il codice
            componente = comp;
            if (componente != null) {
                while (!fine) {
                    if (componente.getClass() == classe) {
                        trovato = componente;
                        fine = true;
                    } else {
                        componente = componente.getParent();
                        if (componente == null) {
                            fine = true;
                        }// fine del blocco if

                    }// fine del blocco if-else
                }// fine del blocco while
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return trovato;
    }


    /**
     * Regola l'altezza di una JTextArea alla misura ottimale per mostrare tutte le righe.
     * <p/>
     * Regola l'altezza di una JTextArea con word wrap attivo.<br>
     * Usa la larghezza esistente (preferredSize)<br>
     * Regola l'altezza in funzione del contenuto.
     *
     * @param area della quale regolare l'altezza Viene regolata l'altezza preferita.
     */
    static void setAreaOptimalHeight(JTextArea area) {
        /* variabili e costanti locali di lavoro */
        View view;
        Dimension size;
        Dimension newSize;

        try { // prova ad eseguire il codice

            /* get the root view of the component */
            view = area.getUI().getRootView(area);

            /* get the current size and set the size of the root
             * view to this size.
             * this normally does not happen until the component
             * is drawn on screen afaik */
            size = area.getPreferredSize();
            view.setSize(size.width, size.height);

            /* retrieve the preferred size of the view according
             * to the wrapping settings and so on */
            newSize = new Dimension(size.width, (int)view.getPreferredSpan(View.Y_AXIS));

            area.setPreferredSize(newSize);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea un filler elastico orizzontale.
     * <p/>
     * E' un componente che si espande orizzontalmente tra gli estremi minimo e massimo. <br>
     *
     * @param min larghezza minima
     * @param pref larghezza preferita
     * @param max larghezza massima
     *
     * @return il filler creato
     */
    static Component createHorizontalFiller(int min, int pref, int max) {
        /* variabili e costanti locali di lavoro */
        Component comp = null;
        Dimension minSize;
        Dimension prefSize;
        Dimension maxSize;
        int altraDim = 1;

        try { // prova ad eseguire il codice
            minSize = new Dimension(min, altraDim);
            prefSize = new Dimension(pref, altraDim);
            maxSize = new Dimension(max, altraDim);
            comp = new Box.Filler(minSize, prefSize, maxSize);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return comp;
    }


    /**
     * Crea un filler elastico orizzontale.
     * <p/>
     * E' un componente che si espande orizzontalmente da zero fino al massimo specificato. <br>
     *
     * @param max larghezza massima
     *
     * @return il filler creato
     */
    static Component createHorizontalFiller(int max) {
        return createHorizontalFiller(0, 0, max);
    }


    /**
     * Crea un filler elastico verticale.
     * <p/>
     * E' un componente che si espande verticalmente tra gli estremi minimo e massimo. <br>
     *
     * @param min altezza minima
     * @param pref altezza preferita
     * @param max altezza massima
     *
     * @return il filler creato
     */
    static Component createVerticalFiller(int min, int pref, int max) {
        /* variabili e costanti locali di lavoro */
        Component comp = null;
        Dimension minSize;
        Dimension prefSize;
        Dimension maxSize;
        int altraDim = 1;

        try { // prova ad eseguire il codice
            minSize = new Dimension(altraDim, min);
            prefSize = new Dimension(altraDim, pref);
            maxSize = new Dimension(altraDim, max);
            comp = new Box.Filler(minSize, prefSize, maxSize);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return comp;
    }


    /**
     * Crea un filler elastico verticale.
     * <p/>
     * E' un componente che si espande verticalmente da zero fino al massimo specificato. <br>
     *
     * @param max altezza massima
     *
     * @return il filler creato
     */
    static Component createVerticalFiller(int max) {
        return createVerticalFiller(0, 0, max);
    }


}// fine della classe
