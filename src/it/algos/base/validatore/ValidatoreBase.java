/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      27-set-2005
 */
package it.algos.base.validatore;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.wrapper.TestoAlgos;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;


/**
 * Validatore astratto di base.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @author Michael Urban
 * @version Beta 1 </p>
 */

public abstract class ValidatoreBase extends InputVerifier implements Validatore {

    /**
     * colore di sfondo del popup di avviso campo non validabile
     */
    private final static Color SFONDO_MESSAGGIO = new Color(243, 255, 159);

    /**
     * popup per la visualizzazione dell'eventuale errore
     */
    private JDialog popup;

    /**
     * messaggio di errore da visualizzare
     */
    private String messaggio;

    /**
     * Etichetta da inserire nel popup
     */
    private JLabel etichetta;

    /**
     * Colore di sfondo originale del componente
     */
    private Color sfondoOriginale;

    /**
     * Flag - se il valore non e' valido, colora lo sfondo anche
     * durante l'inserimento dei singoli caratteri
     */
    private boolean validaKeystroke;


    public ValidatoreBase() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        JDialog popup;
        JLabel etichetta;
        JLabel image;
        Color coloreSfondo;

        try { // prova ad eseguire il codice

            coloreSfondo = SFONDO_MESSAGGIO;
            image = new JLabel(Lib.Risorse.getIconaBase("eccezione16"));

            this.setEtichetta(new JLabel());
            etichetta = this.getEtichetta();
            TestoAlgos.setLegenda(etichetta);
            etichetta.setForeground(Color.red);

            this.setPopup(new JDialog());
            popup = this.getPopup();
            popup.getContentPane().setLayout(new FlowLayout());
            popup.setUndecorated(true);
            popup.getContentPane().setBackground(coloreSfondo);
            popup.getContentPane().add(image);
            popup.getContentPane().add(etichetta);
            popup.setFocusableWindowState(false);

            /* validazione al singolo carattere */
            this.setValidaKeystroke(true);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Associa il componente da validare al validatore
     * Memorizza il colore di sfondo originale del componente
     *
     * @param componente di riferimento
     */
    public void inizializza(JComponent componente) {

        try { // prova ad eseguire il codice

            if (componente != null) {

                /* assegna il verificatore al componente */
                componente.setInputVerifier(this);

                /* memorizza il colore di sfondo originale del componente */
                this.setSfondoOriginale(componente.getBackground());

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Implementazione della logica di validazione.
     * <p/>
     * Implementare la logica di validazione in questo metodo.
     * Ritorna true se il dato e' valido, false se non e' valido.
     * All'interno della validazione e'anche possibile modificare
     * il messaggio in caso di dato non valido usando setMessaggio().
     *
     * @param testo da validare.
     * @param comp componente di riferimento
     *
     * @return true se valido, false se non valido.
     */
    protected abstract boolean validate(String testo, JComponent comp);


    /**
     * Colora lo sfondo del componente.
     * <p/>
     * Metodo invocato da verify (questa classe) <br>
     *
     * @param comp componente associato
     */
    private void coloraSfondo(JComponent comp) {

        try { // prova ad eseguire il codice

            /* colora il campo testo */
            if (comp != null) {
                comp.setBackground(Color.PINK);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Mostra un messaggio di avviso.
     * <p/>
     * Metodo invocato da verify (questa classe) <br>
     *
     * @param comp componente associato
     */
    private void mostraMessaggio(JComponent comp) {
        /* variabili e costanti locali di lavoro */
        Dialog popup;
        int hPop, hComp, wPop, wComp, deltay, deltax, x, y;

        try { // prova ad eseguire il codice

            /* regola il popup con il messaggio */
            this.getEtichetta().setText(this.getMessaggio());

            /* recupera il popup */
            popup = this.getPopup();

            /* posiziona il popup relativamente al componente
             * e lo visualizza */
            popup.pack();
            popup.setLocationRelativeTo(comp);
            Point locPop = popup.getLocation();
            hPop = popup.getHeight();
            hComp = comp.getHeight();
            wPop = popup.getWidth();
            wComp = comp.getWidth();
            deltay = hComp + ((hPop - hComp) / 2) + 2;
            deltax = (wPop - wComp) / 2;
            x = locPop.x + deltax;
            y = locPop.y + deltay;
            popup.setLocation(x, y);
            popup.setVisible(true);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Metodo chiamato direttamente da un componente
     * quando necessita di validazione.
     * <p/>
     *
     * @param componente da validare
     *
     * @return true se il componente e' valido
     */
    public boolean verify(JComponent componente) {
        /* variabili e costanti locali di lavoro */
        boolean verificato = true;
        boolean abilitato;

        try { // prova ad eseguire il codice

            /* controlla se il componente e' abilitato */
            abilitato = componente.isEnabled();

            /* verifica l'uscita dal campo solo se il componente e' abilitato */
            if (abilitato) {
                verificato = this.checkValido(componente, true);
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return verificato;
    }


    /**
     * Controlla se il valore contenuto in un componente e' valido.
     * <p/>
     * Non evidenzia il componente e non visualizza messaggi.
     *
     * @param componente il componente contenente il valore da validare
     *
     * @return true se e' valido, false se non e' valido
     */
    public boolean isValido(JComponent componente) {
        /* variabili e costanti locali di lavoro */
        boolean valido = true;
        String testo;

        try {    // prova ad eseguire il codice

            /* recupera il testo dal componente */
            testo = this.getTestoComponente(componente);

            /* valida il testo nella sottoclasse */
            if (testo != null) {
                valido = this.validate(testo, componente);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Controlla se il valore contenuto in un componente e' valido.
     * <p/>
     * Eventualmente evidenzia il componente e mostra un messaggio.
     * Se non e' valido:
     * - se il parametro evidenza e' true
     * - evidenzia il componente colorando lo sfondo
     * - mostra un messaggio con la motivazione
     *
     * @param componente contenente il valore da validare
     * @param uscitaCampo - true se la chiamata e' dovuta all'uscita dal campo
     * (evidenzia sempre e mostra messaggio se non valido)
     * - false se la chiamata e' originata dall'inserimento di un carattere
     * (evidenzia e mostra messaggio in base alle
     * impostazioni del flag validaKeystroke di questo validatore)
     *
     * @return true se e' valido
     */
    public boolean checkValido(JComponent componente, boolean uscitaCampo) {
        /* variabili e costanti locali di lavoro */
        boolean valido = false;

        try {    // prova ad eseguire il codice

            /* ripristina sfondo originale e nasconde eventuale messaggio */
            componente.setBackground(this.getSfondoOriginale());
            this.getPopup().setVisible(false);

            valido = this.isValido(componente);
            if (!valido) {
                if (uscitaCampo) {
                    this.coloraSfondo(componente);
                    this.mostraMessaggio(componente);
                } else {
                    if (this.isValidaKeystroke()) {
                        this.coloraSfondo(componente);
                    }// fine del blocco if
                }// fine del blocco if-else
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valido;

    }


    /**
     * Ritorna il testo contenuto in un componente.
     * <p/>
     *
     * @param componente dal quale recuperare il testo
     *
     * @return il componente di testo, null se non e' un componente di testo
     */
    private String getTestoComponente(JComponent componente) {
        /* variabili e costanti locali di lavoro */
        String testo = null;
        JTextComponent compTesto = null;

        try {    // prova ad eseguire il codice
            if (componente != null) {
                if (componente instanceof JTextComponent) {
                    compTesto = (JTextComponent)componente;
                    testo = compTesto.getText();
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Regular expression da soddisfare.
     *
     * @param espressione da soddisfare
     */
    public void setEspressione(String espressione) {
    }


    /**
     * Controllo accettazione testo vuoto.
     *
     * @param accettaVuoto true per accettare il testo vuoto
     */
    public void setAccettaTestoVuoto(boolean accettaVuoto) {
    }


    /**
     * Controllo lunghezza minima del testo.
     *
     * @param lunghezzaMinima del testo
     */
    public void setLunghezzaMinima(int lunghezzaMinima) {
    }


    /**
     * Controllo lunghezza massima del testo.
     *
     * @param lunghezzaMassima del testo
     */
    public void setLunghezzaMassima(int lunghezzaMassima) {
    }


    /**
     * Controllo accettazione dello zero.
     *
     * @param accettaZero true per accettare lo zero
     */
    public void setAccettaZero(boolean accettaZero) {
    }


    /**
     * Controllo accettazione numeri negativi.
     *
     * @param accettaNegativi true per accettare i numeri negativi
     */
    public void setAccettaNegativi(boolean accettaNegativi) {
    }


    /**
     * Controllo accettazione numeri positivi.
     *
     * @param accettaPositivi true per accettare i numeri positivi
     */
    public void setAccettaPositivi(boolean accettaPositivi) {
    }


    /**
     * Controllo accettazione numeri decimali.
     * <p/>
     * Sovrascritto dalle sottoclassi.
     *
     * @param accettaDecimali true per accettare i numeri decimali
     */
    public void setAccettaDecimali(boolean accettaDecimali) {
    }


    /**
     * Restituisce il massimo numero di cifre decimali.
     *
     * @return il massimo numero di cifre decimali accettate
     */
    public int getMaxCifreDecimali() {
        /* valore di ritorno */
        return 0;
    }


    /**
     * Controllo massimo numero di cifre decimali.
     *
     * @param maxCifreDecimali il massimo numero di cifre decimali accettate
     */
    public void setMaxCifreDecimali(int maxCifreDecimali) {
    }


    /**
     * Controllo del valore minimo.
     *
     * @param valoreMinimo accettabile
     */
    public void setValoreMinimo(double valoreMinimo) {
    }


    ;


    /**
     * Controllo del valore massimo.
     *
     * @param valoreMassimo accettabile
     */
    public void setValoreMassimo(double valoreMassimo) {
    }


    ;


    private Color getSfondoOriginale() {
        return sfondoOriginale;
    }


    private void setSfondoOriginale(Color sfondoOriginale) {
        this.sfondoOriginale = sfondoOriginale;
    }


    private String getMessaggio() {
        return messaggio;
    }


    /**
     * regola il messaggio da visualizzare in caso di validazione fallita.
     *
     * @param messaggio da visualizzaare
     */
    public void setMessaggio(String messaggio) {
        this.messaggio = messaggio;
    }


    private JDialog getPopup() {
        return popup;
    }


    private void setPopup(JDialog popup) {
        this.popup = popup;
    }


    private JLabel getEtichetta() {
        return etichetta;
    }


    private void setEtichetta(JLabel etichetta) {
        this.etichetta = etichetta;
    }


    private boolean isValidaKeystroke() {
        return validaKeystroke;
    }


    protected void setValidaKeystroke(boolean validaKeystroke) {
        this.validaKeystroke = validaKeystroke;
    }

//    /**
//     * Ritorna una copia profonda dell'oggetto (deep copy) col casting
//     * Per fare una copia completa di questo oggetto occorre:
//     * Prima copiare l'oggetto nel suo insieme, richiamando il metodo
//     * sovrascritto che copia e regola tutte le variabili dell'oggetto con
//     * gli stessi valori delle variabili originarie
//     * Secondo copiare tutte le variabili che sono puntatori ad altri
//     * oggetti, per evitare che nella copia ci sia il puntatore all'oggetto
//     * originale (in genere tutti gli oggetti che vengono creati nella
//     * classe col comando new)
//     * Terzo in ogni sottoclasse occorre fare le copie dei puntatori
//     * esistenti nelle sottoclassi stesse
//     */
//    public Validatore clona() {
//        /* variabili e costanti locali di lavoro */
//        Validatore unValidatore;
//
//        try { // prova ad eseguire il codice
//            /* invoca il metodo sovrascritto della superclasse Object */
//            unValidatore = (Validatore)super.clone();
//        } catch (CloneNotSupportedException unErrore) { // intercetta l'errore
//            throw new InternalError();
//        }// fine del blocco try-catch
//
//
//        /* valore di ritorno */
//        return unValidatore;
//    }


}// fine della classe
