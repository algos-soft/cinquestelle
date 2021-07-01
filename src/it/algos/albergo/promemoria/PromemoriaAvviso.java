package it.algos.albergo.promemoria;

import com.wildcrest.j2printerworks.J2ComponentPrinter;
import it.algos.albergo.camera.Camera;
import it.algos.albergo.camera.CameraModulo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.promemoria.tipo.TipoPro;
import it.algos.albergo.promemoria.tipo.TipoProModulo;
import it.algos.base.azione.adapter.AzAdapterAction;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.campo.dati.CDOra;
import it.algos.base.componente.bottone.BottoneBase;
import it.algos.base.dialogo.DialogoBase;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloBase;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.stampa.Printer;
import it.algos.gestione.anagrafica.Anagrafica;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.Date;

public class PromemoriaAvviso extends DialogoBase implements Promemoria {

    /**
     * codice record della tavola Promemoria
     */
    private int codice;

    /**
     * posizione orizzontale della finestra di dialogo
     */
    private int posX;

    /**
     * posizione verticale della finestra di dialogo
     */
    private int posY;

    /**
     * uso del colore rosso
     */
    private final static boolean ROSSO = true;


    /**
     * Costruttore completo .
     *
     * @param modulo di riferimento
     * @param codice dell'avviso
     * @param posX   orizzontale
     * @param posY   verticale
     */
    public PromemoriaAvviso(Modulo modulo, int codice, int posX, int posY) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        try { // prova ad eseguire il codice
            /* regola le variabili di istanza coi parametri */
            this.setCodice(codice);
            this.setPosX(posX);
            this.setPosY(posY);

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
        this.getDialogo().setModal(false);
        this.getDialogo().setResizable(false);
        this.creaDialogo();
    }// fine del metodo inizia


    /**
     * Creazione dialogo.
     * <p/>
     * Metodo invocato dal bottone specifico nella toolbar della lista <br>
     */
    private void creaDialogo() {
        /* variabili e costanti locali di lavoro */
        BottoneBase bottone;

        try { // prova ad eseguire il codice
            this.setTitolo("Avviso");

            /* regola il contenuto */
            this.regolaContenuto();

            /* bottoni comando */
            bottone = super.addBottoneBase("Stampa", "Print24", false, false, new AzStampa());
            bottone.setToolTipText("stampa questo avviso");
            bottone = super.addBottoneBase("Lista", "CaricaTutti24", false, false, new AzLista());
            bottone.setToolTipText("apre la lista dei promemoria");
            bottone = super.addBottoneBase("+1 ora", "Successivo24", false, false, new AzOra());
            bottone.setToolTipText("rinvia l'avviso di un'ora");
            bottone = super.addBottoneBase("+1 giorno", "Successivo24", false, false, new AzGiorno());
            bottone.setToolTipText("rinvia l'avviso di un giorno");
            bottone = super.addBottoneBase("Ignora", "chiudischeda24", false, false, new AzAnnulla());
            bottone.setToolTipText("ignora momentaneamente l'avviso, che verrà ripresentato");
            bottone = super.addBottoneBase("Eseguito", "Conferma24", false, false, new AzEseguito());
            bottone.setToolTipText("conferma l'esecuzione di quanto previsto e non ripresenta più l'avviso");

            /* lo costruisce non visibile */
            super.avvia(false);

            /* lo sposta e lo rende visibile */
            this.posizionaDialogo();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Contenuto del dialogo.
     */
    private void regolaContenuto() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Modulo mod;
        int cod = 0;
        Pannello panMess = null;
        Pannello pan = null;
        int b = 10;
        int gap = 5;

        try { // prova ad eseguire il codice
            mod = this.getModulo();
            continua = (mod != null);

            if (continua) {
                cod = this.getCodice();
                continua = (cod > 0);
            }// fine del blocco if

            /* regola il pannello */
            if (continua) {
                panMess = super.getPannelloContenuto();
                panMess.getPanFisso().setBorder(BorderFactory.createEmptyBorder(b, b, b, b));
                panMess.setBackground(new Color(255, 251, 179));
                panMess.setOpaque(true);
            }// fine del blocco if

            /* regola i campi */
            if (continua) {
                pan = PannelloFactory.verticale(this);
                pan.setGapFisso(gap);
                pan.add(this.getData(mod, cod));
                pan.add(this.getTitolo(mod, cod));
                pan.add(this.getTesto(mod, cod));
                pan.add(this.getCamera(mod, cod));
                pan.add(this.getCliente(mod, cod));
            }// fine del blocco if

            if (continua) {
                panMess.add(pan);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Recupera la scadenza.
     * <p/>
     * Se l'orario è a mezzanotte, riporta solo la data <br>
     * altrimenti riporta ora e data <br>
     *
     * @param mod modulo Promemoria
     * @param cod codice del record di avviso
     *
     * @return scadenza
     */
    private Pannello getData(Modulo mod, int cod) {
        /* variabili e costanti locali di lavoro */
        Pannello comp = null;
        boolean continua;
        String scadenza;
        int ora = 0;
        String txtOra;

        try { // prova ad eseguire il codice
            cod = this.getCodice();
            continua = (mod != null && cod > 0);

            if (continua) {
                comp = PannelloFactory.orizzontale(this);
                comp.setGapFisso(5);
                ora = mod.query().valoreInt(Promemoria.Cam.oraScadenza.get(), cod);
                continua = (ora >= MEZZANOTTE);
            }// fine del blocco if

            if (continua) {
                scadenza = mod.query().valoreStringa(Promemoria.Cam.dataScadenza.get(), cod);

                if (ora != MEZZANOTTE) {
                    txtOra = CDOra.converti(ora);
                    if (txtOra.startsWith("0")) {
                        txtOra = txtOra.substring(1);
                    }// fine del blocco if

                    scadenza = "Alle " + txtOra + " del " + scadenza;
                }// fine del blocco if
                comp = this.getRigaRossa("Scadenza", scadenza);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return comp;
    }


    /**
     * Recupera il titolo (tipo) dell'avviso.
     * <p/>
     *
     * @param mod modulo Promemoria
     * @param cod codice del record di avviso
     *
     * @return titolo
     */
    private Pannello getTitolo(Modulo mod, int cod) {
        /* variabili e costanti locali di lavoro */
        Pannello comp = null;
        boolean continua;
        int tipo = 0;
        String titolo = "";
        Modulo modTipo = null;

        try { // prova ad eseguire il codice
            cod = this.getCodice();
            continua = (mod != null && cod > 0);

            if (continua) {
                comp = PannelloFactory.orizzontale(this);
                comp.setGapFisso(5);
                tipo = mod.query().valoreInt(Promemoria.Cam.linkTipo.get(), cod);
                continua = (tipo > 0);
            }// fine del blocco if

            if (continua) {
                modTipo = TipoProModulo.get();
                continua = (modTipo != null);
            }// fine del blocco if

            if (continua) {
                titolo = modTipo.query().valoreStringa(TipoPro.Cam.sigla.get(), tipo);
            }// fine del blocco if

            /* costruisce comunque il pannello con o senza titolo */
            comp = this.getRigaRossa("Titolo", titolo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return comp;
    }


    /**
     * Recupera il testo dell'avviso.
     * <p/>
     *
     * @param mod modulo Promemoria
     * @param cod codice del record di avviso
     *
     * @return testo
     */
    private Pannello getTesto(Modulo mod, int cod) {
        /* variabili e costanti locali di lavoro */
        Pannello comp = null;
        boolean continua;
        String testo = "";

        try { // prova ad eseguire il codice
            cod = this.getCodice();
            continua = (mod != null && cod > 0);

            if (continua) {
                comp = PannelloFactory.orizzontale(this);
                comp.setGapFisso(0);
                testo = mod.query().valoreStringa(Promemoria.Cam.testo.get(), cod);
                comp = this.getRigaArea("Testo", testo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return comp;
    }


    /**
     * Recupera la Camera.
     *
     * @param mod modulo Promemoria
     * @param cod codice del record di avviso
     *
     * @return descrizione della camera
     */
    private Pannello getCamera(Modulo mod, int cod) {
        /* variabili e costanti locali di lavoro */
        Pannello comp = null;
        boolean continua;
        Modulo modCamera = null;
        int codCamera;
        String camera = "";

        try { // prova ad eseguire il codice
            cod = this.getCodice();
            continua = (mod != null && cod > 0);

            if (continua) {
                modCamera = CameraModulo.get();
                continua = (modCamera != null && modCamera.isInizializzato());
            }// fine del blocco if

            if (continua) {
                codCamera = mod.query().valoreInt(Promemoria.Cam.rifCamera.get(), cod);
                camera = modCamera.query().valoreStringa(Camera.Cam.camera.get(), codCamera);
                comp = this.getRiga("Camera", camera);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return comp;
    }


    /**
     * Recupera il Cliente, campo soggetto.
     *
     * @param mod modulo Promemoria
     * @param cod codice del record di avviso
     *
     * @return soggetto cliente
     */
    private Pannello getCliente(Modulo mod, int cod) {
        /* variabili e costanti locali di lavoro */
        Pannello comp = null;
        boolean continua;
        Modulo modCliente = null;
        int codCliente;
        String cliente = "";

        try { // prova ad eseguire il codice
            cod = this.getCodice();
            continua = (mod != null && cod > 0);

            if (continua) {
                modCliente = ClienteAlbergoModulo.get();
                continua = (modCliente != null && modCliente.isInizializzato());
            }// fine del blocco if

            if (continua) {
                codCliente = mod.query().valoreInt(Promemoria.Cam.rifCliente.get(), cod);
                cliente = modCliente.query().valoreStringa(Anagrafica.Cam.soggetto.get(), codCliente);
                comp = this.getRiga("Cliente", cliente);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return comp;
    }


    /**
     * Recupera la singola riga.
     * <p/>
     * La riga è composta di due pannelli disposti orizzontalmente <br>
     * Il primo è una JLabel, di dimensione fissa <br>
     * Il secondo è un JtextFild o un JTextArea, sempre di larghezza fissa <br>
     * Il testo del secondo pannello può essere colorato di rosso <br>
     *
     * @param label   di testo fisso a sinistra
     * @param testo   variabile a destra
     * @param isRossa testo della parte destra
     * @param isArea  di tipo JTextArea nella parte destra
     *
     * @return pannello che costituisce la riga
     */
    private Pannello getRiga(String label, String testo, boolean isRossa, boolean isArea) {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;
        Pannello panSin;
        Pannello panDex;
        boolean continua;
        JLabel sin;
        JComponent dex = null;
        int alt = 16;
        int larSin = 65;
        int larDex = 600;
        Campo unCampo = null;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(label));

            if (continua) {
                pan = PannelloFactory.orizzontale(this);
                pan.setGapFisso(3);
                panSin = PannelloFactory.orizzontale(this);
                panSin.setDimFissa(larSin, alt);
                panSin.add(new JLabel(label + ": "));

                panDex = PannelloFactory.orizzontale(this);
                panDex.setDimFissa(larDex, alt);

                if (isArea) {
                    unCampo = CampoFactory.testoArea("nonServe");
                    unCampo.setValore(testo);
                    unCampo.setNumeroRighe(5);
                    unCampo.setLarScheda(larDex);
                    unCampo.setModificabile(false);
                    unCampo.decora().eliminaEtichetta();
                    unCampo.inizializza();
                } else {
                    dex = new JLabel(testo);
                }// fine del blocco if-else

                if (isRossa && ROSSO && dex != null) {
                    dex.setForeground(Color.red);
                }// fine del blocco if
                panDex.add(dex);

                pan.add(panSin);
                if (isArea) {
                    pan.add(unCampo);
                } else {
                    pan.add(dex);
                }// fine del blocco if-else

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Recupera la singola riga.
     * <p/>
     * La riga è composta di due pannelli disposti orizzontalmente <br>
     * Il primo è una JLabel, di dimensione fissa <br>
     * Il secondo è una JLabel, sempre di larghezza fissa <br>
     *
     * @param label di testo fisso a sinistra
     * @param testo variabile a destra
     *
     * @return pannello che costituisce la riga
     */
    private Pannello getRiga(String label, String testo) {
        return this.getRiga(label, testo, false, false);
    }


    /**
     * Recupera la singola riga.
     * <p/>
     * La riga è composta di due pannelli disposti orizzontalmente <br>
     * Il primo è una JLabel, di dimensione fissa <br>
     * Il secondo è una JLabel, sempre di larghezza fissa <br>
     * Il testo del secondo pannello è colorato di rosso <br>
     *
     * @param label di testo fisso a sinistra
     * @param testo variabile a destra
     *
     * @return pannello che costituisce la riga
     */
    private Pannello getRigaRossa(String label, String testo) {
        return this.getRiga(label, testo, true, false);
    }


    /**
     * Recupera la singola riga.
     * <p/>
     * La riga è composta di due pannelli disposti orizzontalmente <br>
     * Il primo è una JLabel, di dimensione fissa <br>
     * Il secondo è una JTextArea, sempre di larghezza fissa <br>
     *
     * @param label di testo fisso a sinistra
     * @param testo variabile a destra
     *
     * @return pannello che costituisce la riga
     */
    private Pannello getRigaArea(String label, String testo) {
        return this.getRiga(label, testo, false, true);
    }


    /**
     * Posiziona il dialogo e lo rende visibile.
     */
    private void posizionaDialogo() {
        /* variabili e costanti locali di lavoro */
        int posX;
        int posY;

        try { // prova ad eseguire il codice
            posX = this.getPosX();
            posY = this.getPosY();
            this.getDialogo().setLocation(posX, posY);
            this.getDialogo().setVisible(true);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Avviso stampa.
     */
    private void stampa() {
        /* variabili e costanti locali di lavoro */
        Printer printer;
        J2ComponentPrinter cp;
        PannelloBase pan;

        try { // prova ad eseguire il codice
            pan = this.getPannelloContenuto().getPanFisso();
            cp = new J2ComponentPrinter(pan);
            printer = Lib.Stampa.getDefaultPrinter();
            printer.addPageable(cp);
            printer.print();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Apre la lista.
     */
    private void lista() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Modulo mod;

        try { // prova ad eseguire il codice
            mod = this.getModulo();
            continua = (mod != null);

            if (continua) {
                mod.avvia();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Sposta avanti di un ora la visualizzazione.
     */
    private void ora() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        int cod = 0;
        Modulo mod;
        int ora = 0;

        try { // prova ad eseguire il codice
            mod = this.getModulo();
            continua = (mod != null);

            if (continua) {
                cod = this.getCodice();
                continua = (cod != 0);
            }// fine del blocco if

            if (continua) {
                ora = mod.query().valoreInt(Promemoria.Cam.oraVisione.get(), cod);
                continua = (ora != 0);
            }// fine del blocco if

            if (continua) {
                ora += 3600;
                mod.query().registra(cod, Promemoria.Cam.oraVisione, ora);
                this.getDialogo().setVisible(false);
                mod.getLista().avvia();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Sposta avanti di un giorno la visualizzazione.
     */
    private void giorno() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        int cod = 0;
        Modulo mod;
        Date giorno = Lib.Data.getVuota();

        try { // prova ad eseguire il codice
            mod = this.getModulo();
            continua = (mod != null);

            if (continua) {
                cod = this.getCodice();
                continua = (cod != 0);
            }// fine del blocco if

            if (continua) {
                giorno = mod.query().valoreData(Promemoria.Cam.dataVisione.get(), cod);
                continua = (Lib.Data.isValida(giorno));
            }// fine del blocco if

            if (continua) {
                giorno = Lib.Data.add(giorno, 1);
                mod.query().registra(cod, Promemoria.Cam.dataVisione, giorno);
                this.getDialogo().setVisible(false);
                mod.getLista().avvia();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Avviso annullato.
     */
    private void annullato() {
        try { // prova ad eseguire il codice
            this.getDialogo().setVisible(false);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Avviso letto ed eseguito.
     */
    private void eseguito() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        int cod = 0;
        Modulo mod;

        try { // prova ad eseguire il codice
            mod = this.getModulo();
            continua = (mod != null);

            if (continua) {
                cod = this.getCodice();
                continua = (cod != 0);
            }// fine del blocco if

            if (continua) {
                mod.query().registra(cod, Promemoria.Cam.eseguito, true);
                this.getDialogo().setVisible(false);
                mod.getLista().avvia();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    private int getCodice() {
        return codice;
    }


    private void setCodice(int codice) {
        this.codice = codice;
    }


    private int getPosX() {
        return posX;
    }


    private void setPosX(int posX) {
        this.posX = posX;
    }


    private int getPosY() {
        return posY;
    }


    private void setPosY(int posY) {
        this.posY = posY;
    }


    /**
     * Azione rinvio od occultamento dell'avviso.
     * </p>
     */
    private final class AzStampa extends AzAdapterAction {

        /**
         * actionPerformed, da ActionListener. </p> Esegue l'azione <br> Rimanda al metodo delegato,
         * nel gestore specifico associato all' oggetto che genera questo evento <br> Sovrascritto
         * nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            stampa();
        }
    } // fine della classe 'interna'


    /**
     * Azione rinvio od occultamento dell'avviso.
     * </p>
     */
    private final class AzLista extends AzAdapterAction {

        /**
         * actionPerformed, da ActionListener. </p> Esegue l'azione <br> Rimanda al metodo delegato,
         * nel gestore specifico associato all' oggetto che genera questo evento <br> Sovrascritto
         * nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            lista();
        }
    } // fine della classe 'interna'


    /**
     * Azione rinvio od occultamento dell'avviso.
     * </p>
     */
    private final class AzOra extends AzAdapterAction {

        /**
         * actionPerformed, da ActionListener. </p> Esegue l'azione <br> Rimanda al metodo delegato,
         * nel gestore specifico associato all' oggetto che genera questo evento <br> Sovrascritto
         * nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            ora();
        }
    } // fine della classe 'interna'


    /**
     * Azione rinvio od occultamento dell'avviso.
     * </p>
     */
    private final class AzGiorno extends AzAdapterAction {

        /**
         * actionPerformed, da ActionListener. </p> Esegue l'azione <br> Rimanda al metodo delegato,
         * nel gestore specifico associato all' oggetto che genera questo evento <br> Sovrascritto
         * nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            giorno();
        }
    } // fine della classe 'interna'


    /**
     * Azione rinvio od occultamento dell'avviso.
     * </p>
     */
    private final class AzAnnulla extends AzAdapterAction {

        /**
         * actionPerformed, da ActionListener. </p> Esegue l'azione <br> Rimanda al metodo delegato,
         * nel gestore specifico associato all' oggetto che genera questo evento <br> Sovrascritto
         * nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            annullato();
        }
    } // fine della classe 'interna'


    /**
     * Azione avviso esguito.
     * </p>
     */
    private final class AzEseguito extends AzAdapterAction {

        /**
         * actionPerformed, da ActionListener. </p> Esegue l'azione <br> Rimanda al metodo delegato,
         * nel gestore specifico associato all' oggetto che genera questo evento <br> Sovrascritto
         * nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            eseguito();
        }
    } // fine della classe 'interna'

}










