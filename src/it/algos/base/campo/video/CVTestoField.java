/**
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 3 luglio 2003 alle 23.53
 */
package it.algos.base.campo.video;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.validatore.Validatore;
import it.algos.base.wrapper.TestoAlgos;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.FocusEvent;

/**
 * Componente video di tipo testo edit.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Crea il componente GUI specifico di questa classe </li>
 * <li> Aggiunge i Listener al componente specifico </li>
 * <li> Regola i font, la dimensione e la posizione del componente specifico </li>
 * <li> Regola le dimensioni del pannelloComponenti </li>
 * <li> Implementa i metodi astratti della superclasse per regolare le
 * conversioni tra i vari tipi di dati </li>
 * <li> L'attributo <i>video</i> del <code>CampoDati</code> associato,
 * <strong>deve</strong> essere di tipo testo </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  3 luglio 2003 ore 23.53
 */
public class CVTestoField extends CVTesto {

    /**
     * larghezza di default in scheda
     */
    private static final int LARGHEZZA_DEFAULT = 150;


    /* oggetto contenente i formattatori di display ed editing
     * assegnato al componente video */
    private DefaultFormatterFactory formatterFactory;

    /**
     * tipo di allineamento del testo all'interno del componente
     */
    private int allineamento;


    /**
     * Costruttore completo con parametri.
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CVTestoField(Campo unCampoParente) {
        /* rimanda al costruttore della superclasse */
        super(unCampoParente);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /* normalmente questo campo ha una sola riga */
        this.setNumeroRighe(1);

        /* larghezza di default dei componenti interni al pannello componenti */
        this.getCampoParente().setLarScheda(LARGHEZZA_DEFAULT);

        /* creazione dei componenti interni */
        this.creaComponentiInterni();

        /* di default i testi vanno a sinistra */
        this.setAllineamento(SwingConstants.LEFT);

    }


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alle impostazioni
     * correnti delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto (di norma) <br>
     * Viene eseguito una sola volta <br>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void inizializza() {

        try { // prova ad eseguire il codice

            /* associa l'eventuale validatore al componente video */
            this.associaValidatore();

            /* crea il FormatterFactory con i filtri di display ed editing */
            this.creaFormatterFactory();

            /* assegna il formatter factory al componente video */
            this.assegnaFormatter();

            /* regola l'allineamento */
            this.regolaAllineamento();

            /* inizializza nella superclasse */
            super.inizializza();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Resetta la property Value del JFormattedTextField.
     * Serve per evitare che i JFormattedTextFields mantengano
     * il Value precedente (ultimo edit valido) tra un avvio e l'altro.
     * Se così fosse, un editing invalido di un JFormattedTextField
     * ripristinerebbe l'ultimo valore valido inserito, probabilmente
     * in un'altra scheda, che non ha senso.
     * <p/>
     */
    public void avvia() {
        /* variabili e costanti locali di lavoro */
        JFormattedTextField compFormatted;


        try { // prova ad eseguire il codice

            compFormatted = this.getCompSpecifico();
            if (compFormatted != null) {
                compFormatted.setValue(null);
            }// fine del blocco if

            super.avvia();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    protected void regolaLarghezzaComponenti() {
        super.regolaLarghezzaComponenti();
    }


    /**
     * Crea il FormatterFactory per il componente video.
     * <p/>
     * Invocato dal metodo Inizializza.
     * Crea un oggetto DefaultFormatterFactory, lo regola
     * con i formatters di display ed editing e lo registra.
     */
    private void creaFormatterFactory() {
        /* variabili e costanti locali di lavoro */
        JFormattedTextField.AbstractFormatter ef;
        JFormattedTextField.AbstractFormatter df;
        DefaultFormatterFactory ff;

        try {    // prova ad eseguire il codice
            df = this.getCampoDati().getDisplayFormatter();
            ef = this.getCampoDati().getEditFormatter();
            ff = new DefaultFormatterFactory();
            ff.setDefaultFormatter(ef);
            ff.setDisplayFormatter(ef);
            ff.setEditFormatter(ef);
            this.setFormatterFactory(ff);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Assegna i formatter al componente video.
     * <p/>
     * Assegna il FormatterFactory al componente video
     */
    protected void assegnaFormatter() {
        /* variabili e costanti locali di lavoro */
        DefaultFormatterFactory ff;
        JFormattedTextField componente;
        String testo;

        try {    // prova ad eseguire il codice

            ff = this.getFormatterFactory();
            componente = this.getCompSpecifico();

            // todo da rivedere crea incremento di memoria con i cloni
            testo = this.getComponenteTesto().getText();
            componente.setFormatterFactory(ff);

            /* ripristina il testo del componente
             * (il video viene modificato assegnando il FormatterFactory)*/
            this.getComponenteTesto().setText(testo);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea i componenti interni al pannelloComponenti.
     * <p/>
     * Metodo invocato dal ciclo inizia <br>
     * Sovrascrive il metodo della superclasse <br>
     * E' responsabilita' di questo metodo:
     * - creare i componenti GUI interni al PannelloComponenti<br>
     * - eventualmente assegnare un layout al PannelloComponenti<br>
     * - aggiungere i componenti al pannelloComponenti<br>
     */
    public void creaComponentiInterni() {
        /* variabili e costanti locali di lavoro */
        JFormattedTextField comp;

        try { // prova ad eseguire il codice

            /* crea il componente principale di tipo JTextField */
            comp = new AlgosFormattedTextField();

            /* registra il riferimento al componente principale */
            this.setComponente(comp);

            /* registra il riferimento al componente di testo accessibile
             * (in questo caso e' lo stesso)*/
            this.setComponenteTesto(comp);

            /* regola colore e font del componente */
            TestoAlgos.setField(comp);

            /* rimanda al metodo della superclasse */
            super.creaComponentiInterni();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Associa l'eventuale validatore al componente video.
     * <p/>
     * Recupera l'eventuale validatore e lo inizializza passandogli
     * il componente video da validare.
     * Associa cosi' i due oggetti in modo incrociato.
     */
    private void associaValidatore() {
        /* variabili e costanti locali di lavoro */
        JFormattedTextField comp;
        Validatore validatore;

        try {    // prova ad eseguire il codice

            /* rimuove l'eventuale verificatore dal componente video */
            this.getComponente().setInputVerifier(null);

            /* assegna il verificatore al componente */
            validatore = this.getCampoDati().getValidatore();
            if (validatore != null) {
                comp = this.getCompSpecifico();
                validatore.inizializza(comp);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna il componente di testo sepcifico.
     * <p/>
     * Il componente e' di tipo JFormattedTextField.
     *
     * @return il componente specifico
     */
    protected JFormattedTextField getCompSpecifico() {
        /* variabili e costanti locali di lavoro */
        JFormattedTextField compSpecifico = null;
        JTextComponent compTesto;

        try {    // prova ad eseguire il codice
            compTesto = this.getComponenteTesto();
            if (compTesto != null) {
                if (compTesto instanceof JFormattedTextField) {
                    compSpecifico = (JFormattedTextField)compTesto;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return compSpecifico;
    }


    /**
     * Ritorna il codice di allineamento del testo.
     * <p/>
     *
     * @return il codice dalle costanti di allineamento JTextField
     *         JTextField.LEFT
     *         JTextField.CENTER
     *         JTextField.RIGHT
     *         JTextField.LEADING
     *         JTextField.TRAILING
     */
    private int getAllineamento() {
        return allineamento;
    }


    /**
     * Regola l'allineamento del testo.
     * <p/>
     * Metodo sovrascritto dalle sottoclassi<br>
     *
     * @param allineamento codice di allineamento
     *
     * @see javax.swing.SwingConstants
     *      SwingConstants.LEFT
     *      SwingConstants.CENTER
     *      SwingConstants.RIGHT
     *      SwingConstants.LEADING
     *      SwingConstants.TRAILING
     */
    public void setAllineamento(int allineamento) {
        /* variabili e costanti locali di lavoro */
        int allJTextField;

        try { // prova ad eseguire il codice

            /* Converte da costanti Swing a costanti JTextField */
            switch (allineamento) {
                case SwingConstants.LEFT:
                    allJTextField = JTextField.LEFT;
                    break;
                case SwingConstants.CENTER:
                    allJTextField = JTextField.CENTER;
                    break;
                case SwingConstants.RIGHT:
                    allJTextField = JTextField.RIGHT;
                    break;
                case SwingConstants.LEADING:
                    allJTextField = JTextField.LEADING;
                    break;
                case SwingConstants.TRAILING:
                    allJTextField = JTextField.TRAILING;
                    break;
                default: // caso non definito
                    allJTextField = JTextField.LEFT;
            } // fine del blocco switch

            this.allineamento = allJTextField;
            this.regolaAllineamento();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Regola l'allineamento del testo.
     * <p/>
     * Metodo invocato da inizializza <br>
     *
     * @see javax.swing.JTextField
     *      JTextField.LEFT
     *      JTextField.CENTER
     *      JTextField.RIGHT
     *      JTextField.LEADING
     *      JTextField.TRAILING
     */
    private void regolaAllineamento() {
        /* variabili e costanti locali di lavoro */
        JTextField compTesto;
        int allineamento;

        try { // prova ad eseguire il codice
            allineamento = this.getAllineamento();

            compTesto = this.getCompSpecifico();
            if (compTesto != null) {
                compTesto.setHorizontalAlignment(allineamento);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Invocato quando un componente del campo video ha
     * acquisito il fuoco in maniera permanente.
     * <p/>
     * Sovrascritto dalle sottoclassi.
     *
     * @param e l'evento fuoco
     */
    protected void focusGainedComponente(FocusEvent e) {
        super.focusGainedComponente(e);

        /* riassegna il formatterFactory ogni volta che il campo
         * prende il fuoco - se no funziona male sui cloni */
        this.assegnaFormatter();
    }


    /**
     * Invocato quando si entra in un campo (prende il fuoco).
     * <p/>
     */
    public void entrataCampo() {

        super.entrataCampo();

        /* Seleziona tutto il testo dopo che ha preso il fuoco */
        this.selectAll();

    }


    private DefaultFormatterFactory getFormatterFactory() {
        return formatterFactory;
    }


    private void setFormatterFactory(DefaultFormatterFactory formatterFactory) {
        this.formatterFactory = formatterFactory;
    }


    /**
     * Componente JFormattedTextField specifico
     * </p>
     * Sovrascrive setText() perché Java dopo questo comando
     * sposta il cursore in fondo al testo e se il testo è troppo lungo
     * e non ci sta nella parte visibile del campo lo allinea a destra
     * anziché a sinistra.
     */
    private final class AlgosFormattedTextField extends JFormattedTextField {

        public void setText(String string) {
            super.setText(string);
            this.getCaret().setDot(0); // riposiziona il cursore a sinistra
        }
    } // fine della classe 'interna'


    public class DocumentSizeFilter extends DocumentFilter {

        int maxCharacters;

        boolean DEBUG = false;


        public DocumentSizeFilter(int maxChars) {
            maxCharacters = maxChars;
        }


        public void insertString(FilterBypass fb, int offs, String str, AttributeSet a) throws
                BadLocationException {
            if (DEBUG) {
                System.out.println("in DocumentSizeFilter's insertString method");
            }

            //This rejects the entire insertion if it would make
            //the contents too long. Another option would be
            //to truncate the inserted string so the contents
            //would be exactly maxCharacters in length.
            if ((fb.getDocument().getLength() + str.length()) <= maxCharacters) {
                super.insertString(fb, offs, str, a);
            } else {
                Lib.Sist.beep();
            }
        }


        public void replace(FilterBypass fb,
                            int offs,
                            int length,
                            String str,
                            AttributeSet a) throws BadLocationException {
            if (DEBUG) {
                System.out.println("in DocumentSizeFilter's replace method");
            }
            //This rejects the entire replacement if it would make
            //the contents too long. Another option would be
            //to truncate the replacement string so the contents
            //would be exactly maxCharacters in length.
            if ((fb.getDocument().getLength() + str.length() - length) <= maxCharacters) {
                super.replace(fb, offs, length, str, a);
            } else {
                Lib.Sist.beep();
            }
        }


    }

}// fine della classe