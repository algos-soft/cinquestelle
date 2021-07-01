package it.algos.albergo.arrivipartenze;

import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.conto.Conto;
import it.algos.albergo.conto.ContoModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.lista.TavolaModello;
import it.algos.base.modulo.Modulo;
import it.algos.base.tavola.Tavola;
import it.algos.base.tavola.renderer.RendererBase;
import it.algos.gestione.anagrafica.Anagrafica;

import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.Component;

/**
 * Classe 'interna'
 * <p/>
 * Renderer del campo selezione del conto
 */
final class RendererConto extends RendererBase {

    private JButton bottone;


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param campo di riferimento
     */
    public RendererConto(Campo campo) {
        /* rimanda al costruttore della superclasse */
        super(campo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice
            this.setBottone(ConfermaArrivoDialogo.getBottoneLista());
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public Component getTableCellRendererComponent(JTable table,
                                                   Object oggetto,
                                                   boolean isSelected,
                                                   boolean b,
                                                   int riga,
                                                   int colonna) {
        /* variabili e costanti locali di lavoro */
        JButton bottone = null;
        boolean abilitato;
        String stringa = "";

        try { // prova ad eseguire il codice
            bottone = this.getBottone();
            abilitato = ConfermaArrivoDialogo.isRigaSpuntata(table, riga);
            bottone.setEnabled(abilitato);
            if (abilitato) {
                bottone.setForeground(table.getSelectionForeground());
                bottone.setBackground(table.getSelectionBackground());
                stringa = this.getStringaConto(table, riga);
            } else {

                if (isSelected) {
                    bottone.setBackground(table.getSelectionBackground());
                    bottone.setForeground(table.getSelectionForeground());
                } else {
                    bottone.setBackground(table.getBackground());
                    bottone.setForeground(table.getForeground());
                }

            }// fine del blocco if-else
            bottone.setText(stringa);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return bottone;
    }


    /**
     * Ritorna la stringa conto da visualizzare
     * <p/>
     *
     * @param table la jtable
     * @param riga da esaminare
     * return la stringa conto
     */
    private String getStringaConto(JTable table, int riga) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        int linkContoCliente;
        try {    // prova ad eseguire il codice
            linkContoCliente = this.getLink(table, riga);
            if (this.isNuovoConto(table, riga)) {
                stringa = this.getCliente(linkContoCliente);
            } else {
                stringa = this.getConto(linkContoCliente);
            }// fine del blocco if-else
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Ritorna il codice cliente relativo a una riga.
     * <p/>
     *
     * @param table la jtable
     * @param riga da esaminare
     *
     * @return il codice cliente
     */
    private int getCodCliente(JTable table, int riga) {
        /* variabili e costanti locali di lavoro */
        int codcli = 0;
        boolean continua = true;
        Tavola tavola = null;
        TavolaModello modelloDati;
        Dati dati;

        try {    // prova ad eseguire il codice

            if (table == null) {
                continua = false;
            }// fine del blocco if

            if (continua) {
                if (table instanceof Tavola) {
                    tavola = (Tavola)table;
                } else {
                    continua = false;
                }// fine del blocco if-else
            }// fine del blocco if

            if (continua) {
                modelloDati = tavola.getModello();
                dati = modelloDati.getDati();
                codcli = dati.getIntAt(riga, ConfermaArrivoDialogo.Nomi.codCliente.get());
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codcli;
    }

//    /**
//     * Ritorna true se una riga è spuntata col check di arrivo.
//     * <p/>
//     *
//     * @param table la jtable
//     * @param riga da esaminare
//     *
//     * @return true se spuntata
//     */
//    private boolean isSpuntata(JTable table, int riga) {
//        /* variabili e costanti locali di lavoro */
//        boolean spuntata = false;
//        boolean continua = true;
//        Tavola tavola = null;
//        TavolaModello modelloDati;
//        Dati dati;
//
//        try {    // prova ad eseguire il codice
//
//            if (table == null) {
//                continua = false;
//            }// fine del blocco if
//
//            if (continua) {
//                if (table instanceof Tavola) {
//                    tavola = (Tavola)table;
//                } else {
//                    continua = false;
//                }// fine del blocco if-else
//            }// fine del blocco if
//
//            if (continua) {
//                modelloDati = tavola.getModello();
//                dati = modelloDati.getDati();
//                spuntata = dati.getBoolAt(riga, ConfermaArrivoDialogo.Nomi.check.get());
//            }// fine del blocco if
//
//        } catch (Exception unErrore) {    // intercetta l'errore
//            Errore.crea(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return spuntata;
//    }


    /**
     * Ritorna il flag nuovo conto.
     * <p/>
     *
     * @param table la jtable
     * @param riga da esaminare
     *
     * @return true se nuovo conto
     */
    private boolean isNuovoConto(JTable table, int riga) {
        /* variabili e costanti locali di lavoro */
        boolean spuntata = false;
        boolean continua = true;
        Tavola tavola = null;
        TavolaModello modelloDati;
        Dati dati;

        try {    // prova ad eseguire il codice

            if (table == null) {
                continua = false;
            }// fine del blocco if

            if (continua) {
                if (table instanceof Tavola) {
                    tavola = (Tavola)table;
                } else {
                    continua = false;
                }// fine del blocco if-else
            }// fine del blocco if

            if (continua) {
                modelloDati = tavola.getModello();
                dati = modelloDati.getDati();
                spuntata = dati.getBoolAt(riga, ConfermaArrivoDialogo.Nomi.nuovoconto.get());
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return spuntata;
    }


    /**
     * Ritorna il codice link conto o cliente relativo a una riga.
     * <p/>
     *
     * @param table la jtable
     * @param riga da esaminare
     *
     * @return il codice cliente
     */
    private int getLink(JTable table, int riga) {
        /* variabili e costanti locali di lavoro */
        int codlink = 0;
        boolean continua = true;
        Tavola tavola = null;
        TavolaModello modelloDati;
        Dati dati;

        try {    // prova ad eseguire il codice

            if (table == null) {
                continua = false;
            }// fine del blocco if

            if (continua) {
                if (table instanceof Tavola) {
                    tavola = (Tavola)table;
                } else {
                    continua = false;
                }// fine del blocco if-else
            }// fine del blocco if

            if (continua) {
                modelloDati = tavola.getModello();
                dati = modelloDati.getDati();
                codlink = dati.getIntAt(riga, ConfermaArrivoDialogo.Nomi.linkcontocliente.get());
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codlink;
    }


    /**
     * Ritorna la sigla di un cliente.
     * <p/>
     */
    private String getCliente(int codice) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        Modulo mod;

        try {    // prova ad eseguire il codice
            mod = ClienteAlbergoModulo.get();
            stringa = mod.query().valoreStringa(Anagrafica.Cam.soggetto.get(), codice);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    /**
     * Ritorna la sigla di un conto.
     * <p/>
     */
    private String getConto(int codice) {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        Modulo mod;

        try {    // prova ad eseguire il codice
            mod = ContoModulo.get();
            stringa = mod.query().valoreStringa(Conto.Cam.sigla.get(), codice);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    private JButton getBottone() {
        return bottone;
    }


    private JButton setBottone(JButton bottone) {
        this.bottone = bottone;
        return getBottone();
    }


}
