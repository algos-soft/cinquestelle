/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      14-feb-2007
 */
package it.algos.base.aggiornamento;

import it.algos.base.dialogo.Dialogo;
import it.algos.base.dialogo.DialogoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.pref.Pref;
import it.algos.base.progetto.Progetto;
import it.algos.base.wrapper.FtpWrap;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Classe astratta con metodi statici.
 * </p>
 * Questa classe astratta: <ul>
 * <li>  </li>
 * <li>  </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 14-feb-2007 ore 13.49.25
 */
public class Aggiornamento {

    private static final String DIR_LOC_LIB = "/lib";

    private String server;

    private String utente;

    private String password;

    private String cartella;

    private ArrayList<FileWrap> listaFiles;


    /**
     * Costruttore completo con parametri.
     */
    public Aggiornamento() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
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
    }


    /**
     * Controlla se esistono i dati necessari per aggiornare.
     * <p/>
     *
     * @return se ci sono tuttti i dati
     */
    private boolean isDatiServer() {
        /* variabili e costanti locali di lavoro */
        boolean esistono = true;
        String nomeServer;
        String nomeUtente;
        String password;
        String cartella;

        try { // prova ad eseguire il codice

            /* indirizzo del server */
            nomeServer = Pref.Update.serverAgg.str();
            if (Lib.Testo.isValida(nomeServer)) {
                setServer(nomeServer);
            } else {
                esistono = false;
            }// fine del blocco if-else

            /* nome utente sul server FTP */
            nomeUtente = Pref.Update.nomeAgg.str();
            if (Lib.Testo.isValida(nomeUtente)) {
                setUtente(nomeUtente);
            } else {
                esistono = false;
            }// fine del blocco if-else

            /* password del server FTP */
            password = Pref.Update.passAgg.str();
            if (Lib.Testo.isValida(password)) {
                setPassword(password);
            } else {
                esistono = false;
            }// fine del blocco if-else

            /* cartella sul server FTP */
            cartella = Progetto.getCatUpdate();
            if (Lib.Testo.isValida(cartella)) {
                this.setCartella(cartella);
            } else {
                esistono = false;
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return esistono;
    }


    /**
     * Restituisce il collegamento, se questo è possibile e congruo.
     * <p/>
     * Controlla che esista il collegamento internet <br>
     * Controlla che il server sia acceso <br>
     * Controlla che l'accesso al server (nick e password) sia abilitato <br>
     * Controlla che sul server esista la cartella (directory) del programma <br>
     *
     * @param avvisa messaggi di avviso visibili
     *
     * @return un collegamento valido
     */
    private FtpWrap getConnessione(boolean avvisa) {
        /* variabili e costanti locali di lavoro */
        FtpWrap ftpValido = null;
        FtpWrap ftp;
        boolean continua = true;
        String indirizzo;
        int reply;
        String dirServer;

        try { // prova ad eseguire il codice
            dirServer = this.getDirServer();
            ftp = new FtpWrap();

            /* non costringe la che la risposta venga dallo stesso indirizzo chiamato */
            ftp.setRemoteVerificationEnabled(false);

            indirizzo = getServer();

            // todo regolare il timeout della connessione!
            try { // prova ad eseguire il codice
                ftp.connect(indirizzo);
                // After connection attempt, you should check the reply code to verify
                // success.
                reply = ftp.getReplyCode();

                if (!FTPReply.isPositiveCompletion(reply)) {
                    ftp.disconnect();
                    System.err.println("FTP server refused connection.");
                    continua = false;
                }

            } catch (ConnectException unErrore) { // intercetta l'errore
                if (avvisa) {
                    new MessaggioAvviso("Non sono riuscito a collegarmi");
                }// fine del blocco if
                continua = false;
            }// fine del blocco try-catch

            /* effettua il login */
            if (continua) {
                if (ftp.login(getUtente(), getPassword())) {
                    continua = true;
                } else {
                    if (avvisa) {
                        new MessaggioAvviso("Nome utente e password non sono corretti");
                    }// fine del blocco if
                    continua = false;
                }// fine del blocco if-else
            }// fine del blocco if

            /* regola la sessione FTP */
            if (continua) {
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
                ftp.enterLocalPassiveMode();
            }// fine del blocco if-else

            /* seleziono la directory di aggiornamento */
            if (continua) {
                if (ftp.changeWorkingDirectory(dirServer)) {
                    continua = true;
                } else {
                    if (avvisa) {
                        new MessaggioAvviso("Non esiste la cartella del progetto");
                    }// fine del blocco if
                    continua = false;
                }// fine del blocco if-else
            }// fine del blocco if

            /* assegna un valore valido di uscita */
            if (continua) {
                ftpValido = ftp;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return ftpValido;
    }


    /**
     * Metodo statico.
     * <p/>
     * Controlla se esistono i dati necessari per aggiornare <br>
     * Controlla se il server è accessibile <br>
     * Controlla se ci sono aggiornamenti da scaricare <br>
     * Controlla se deve chiedere conferma <br>
     * Scarica e sostituisce <br>
     *
     * @param avvisa messaggi di avviso visibili
     *
     * @return se il programma ha subito aggiornamenti e deve essere riavviato
     *
     * @see it.algos.base.progetto.Progetto#lanciaIstanza(it.algos.base.modulo.Modulo) ;
     */
    private boolean esegueBase(boolean avvisa) {
        /* variabili e costanti locali di lavoro */
        boolean esci = false;
        boolean continua;
        boolean aggiornato;
        FtpWrap ftp = null;
        String mess;
        String messKilo;
        String messKiloNum;
        String messKiloPost;
        String messKiloPost2;
        String messPost;
        String messaggio;
        String fallito;
        String riuscito;
        Dialogo dialogo;

        try { // prova ad eseguire il codice
            mess = "Sul sito della Algos s.r.l. ho trovato degli aggiornamenti";
            mess += " con nuove funzionalità per il programma.";
            messKilo = "\nSono circa ";
            messKiloPost = " kilobyte (";
            messKiloPost2 = " files)";
            messPost = "\nVuoi scaricarli?";

            fallito = "Non sono riuscito a scaricare gli aggiornamenti";

            riuscito = "Gli aggiornamenti sono stati scaricati e";
            riuscito += "\nsaranno operativi al prossimo riavvio del programma";
            riuscito += "\nVuoi uscire adesso dal programma?";

            /* Controlla se esistono i dati necessari per aggiornare */
            if (isDatiServer()) {
                continua = true;
            } else {
                if (avvisa) {
                    new MessaggioAvviso(
                            "I dati delle preferenze non sono validi per effettuare il collegamento");
                }// fine del blocco if
                continua = false;
            }// fine del blocco if-else

            /* Controlla se il server è accessibile */
            if (continua) {
                ftp = getConnessione(avvisa);
                continua = (ftp != null);
            }// fine del blocco if

            if (continua) {
                if (isAggiornamenti(ftp)) {

                    dialogo = DialogoFactory.annullaConferma("Aggiornamenti");
                    messKiloNum = this.getKilobyte();
                    messKiloPost += this.getListaFiles().size();
                    messaggio =
                            mess + messKilo + messKiloNum + messKiloPost + messKiloPost2 + messPost;
                    dialogo.setMessaggio(messaggio);
                    dialogo.avvia();
                    if (dialogo.isConfermato()) {

                        aggiornato = esegueAggiornamenti(ftp);
                        if (aggiornato) {
                            registraUpdate();
                            dialogo = DialogoFactory.annullaConferma("Riavvio");
                            dialogo.setMessaggio(riuscito);
                            dialogo.avvia();
                            if (dialogo.isConfermato()) {
                                System.exit(0);
                            }// fine del blocco if
                        } else {
                            new MessaggioAvviso(fallito);
                        }// fine del blocco if-else
                    }// fine del blocco if
                } else {
                    if (avvisa) {
                        new MessaggioAvviso("Non ci sono aggiornamenti disponibili");
                    }// fine del blocco if
                }// fine del blocco if-else

                /* chiude la connessione */
                ftp.disconnect();

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return esci;
    }


    /**
     * Controlla se esistono aggiornamenti.
     * <p/>
     *
     * @param lista di file inesistenti o modificati
     * @param file da aggiungere
     */
    private void addFile(ArrayList<FileWrap> lista, FTPFile file) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        FileWrap wrap;
        long dim;
        int kilo;
        String nome = "";

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (lista != null && file != null);

            if (continua) {
                nome = file.getName();
                continua = (Lib.Testo.isValida(nome));
            }// fine del blocco if

            if (continua) {
                dim = file.getSize();
                kilo = (int)(dim / 1000);
                wrap = new FileWrap(nome, kilo);
                lista.add(wrap);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Controlla se esistono aggiornamenti.
     * <p/>
     *
     * @param ftp collegamento valido
     *
     * @return se esistono aggiornamenti
     */
    private boolean isAggiornamenti(FtpWrap ftp) {
        /* variabili e costanti locali di lavoro */
        boolean esistono = false;
        boolean continua;
        String dirServer = "";
        String dirLocLib = "";
        ArrayList<FTPFile> listaServer = null;
        ArrayList<FileWrap> listaFiles = null;
        String nome;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (ftp != null);

            if (continua) {
                dirServer = this.getDirServer();
                dirLocLib = this.getDirLibrerie();
                continua = (Lib.Testo.isValida(dirServer) && Lib.Testo.isValida(dirLocLib));
            }// fine del blocco if

            /* posiziona il collegamento sulla cartella interessata */
            if (continua) {
                continua = ftp.changeWorkingDirectory(dirServer);
            }// fine del blocco if

            /* estrae tutti i files presenti nella cartella del server */
            if (continua) {
                listaServer = Lib.Web.getFiles(dirServer, ftp);
                continua = (listaServer != null && listaServer.size() > 0);
            }// fine del blocco if

            if (continua) {
                listaFiles = new ArrayList<FileWrap>();
                for (FTPFile fileSulServer : listaServer) {
                    nome = fileSulServer.getName();

                    if (Lib.File.isEsisteFile(dirLocLib + "/" + nome)) {
                        if (isVecchio(ftp, nome)) {
                            this.addFile(listaFiles, fileSulServer);
                        }// fine del blocco if
                    } else {
                        this.addFile(listaFiles, fileSulServer);
                    }// fine del blocco if-else
                } // fine del ciclo for-each
            }// fine del blocco if

            if (continua) {
                this.setListaFiles(listaFiles);
                esistono = listaFiles.size() > 0;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return esistono;
    }


    /**
     * Esegue gli aggiornamenti.
     * <p/>
     * 1° pulisce la cartella tmp da tutti i files: log ed eventuali file rimasti
     * (interi o a metà) da un precedente scarico non terminato <br>
     * 2° scarica i files dal server <br>
     * 3° costruisce il file log col tag OK di controllo <br>
     *
     * @param ftp collegamento valido
     *
     * @return se esistono aggiornamenti
     */
    private boolean esegueAggiornamenti(FtpWrap ftp) {
        /* variabili e costanti locali di lavoro */
        boolean aggiornato = false;
        boolean continua;
        boolean riuscito;
        String dirLocTmp = "";
        String dirUpdate = "";
        ArrayList<FileWrap> listaFiles = null;
        String pathLog = "/log.txt";
        String testoLog = "OK";
        String nomeFile;
        String pathServer;
        String pathLocTmp;
        File file;
        String tag = "/";

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (ftp != null);

            if (continua) {
                dirLocTmp = this.getDirTemporanea();

                if (dirLocTmp.contains(tag)) {
                    dirUpdate = Lib.Testo.primaUltima(dirLocTmp, tag);
                    file = new File(dirUpdate);
                    if (!file.exists()) {
                        file.mkdir();
                    }// fine del blocco if
//                        file.mkdir();
//                        int a=87;
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {
                listaFiles = this.getListaFiles();
                continua = (listaFiles != null && listaFiles.size() > 0);
            }// fine del blocco if

            /* cancella il contenuto della cartella */
            if (continua) {
                Lib.File.cancellaContenutoDir(dirLocTmp);
            }// fine del blocco if

            if (continua) {
                for (FileWrap wrap : listaFiles) {
                    nomeFile = wrap.getNome();
                    pathServer = this.getPathServer(nomeFile);
                    pathLocTmp = this.getPathTmp(nomeFile);
                    riuscito = copiaFile(ftp, pathServer, pathLocTmp);
                    if (!riuscito) {
                        continua = false;
                        break;
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

            /* costruisce il file log di conferma */
            if (continua) {
                aggiornato = true;
                pathLog = dirLocTmp + pathLog;
                Lib.File.scrive(pathLog, testoLog);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return aggiornato;
    }


    /**
     * Copia un file dal server alla destinazione locale.
     * <p/>
     *
     * @param ftp collegamento attivo
     * @param pathServer indirizzo completo sul server
     * @param pathLocale indirizzo completo in locale
     *
     * @return se riuscito
     */
    private boolean copiaFile(FTPClient ftp, String pathServer, String pathLocale) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        OutputStream output;
        String dirName;
        boolean esiste;
        boolean continua = true;

        try { // prova ad eseguire il codice

            /* se non esiste la cartella temporanea la crea ora */
            dirName = this.getDirTemporanea();
            esiste = Lib.File.isEsisteFile(dirName);
            if (!esiste) {
                continua = (new File(dirName)).mkdir();
            }// fine del blocco if

            if (continua) {
                output = new FileOutputStream(pathLocale);
                riuscito = ftp.retrieveFile(pathServer, output);
                output.close();
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Controlla se un file è vecchio.
     * <p/>
     * un file è considerato vecchio se la data/ora sul server
     * è posteriore alla della data/ora locale
     *
     * @param ftp collegamento attivo
     * @param nomeFile nome del file da confrontare
     *
     * @return se riuscito
     */
    private boolean isVecchio(FtpWrap ftp, String nomeFile) {
        /* variabili e costanti locali di lavoro */
        boolean vecchio = false;
        boolean continua;
        Date dataServer = null;
        Date dataLocale = null;
        String pathServer;
        String pathLocLib;
        long timeServer = 0;
        long timeLocale = 0;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (ftp != null && Lib.Testo.isValida(nomeFile));

            if (continua) {
                pathServer = this.getPathServer(nomeFile);
                dataServer = Lib.Web.getData(pathServer, ftp);
                continua = (dataServer != null);
            }// fine del blocco if

            if (continua) {
                pathLocLib = this.getPathLib(nomeFile);
                dataLocale = Lib.File.getData(pathLocLib);
                continua = (dataLocale != null);
            }// fine del blocco if

            if (continua) {
                timeServer = dataServer.getTime();
                timeLocale = dataLocale.getTime();
            }// fine del blocco if

            if (continua) {
                vecchio = (timeServer > timeLocale);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return vecchio;
    }


    /**
     * Controlla se il periodo di aggiornamento è scaduto.
     * <p/>
     * Recupera l'ultimo aggiornamento dalle preferenze <br>
     * Recupera l'intervallo dalle preferenze <br>
     *
     * @return da aggiornare
     */
    private boolean isAggiornamentoScaduto() {
        /* variabili e costanti locali di lavoro */
        boolean scaduto = false;
        boolean continua;
        Date oggi;
        Date ultimoAgg = null;
        int giorniPrevisti = 0;
        int giorniPassati;

        try { // prova ad eseguire il codice
            oggi = Lib.Data.getCorrente();
            continua = (oggi != null);

            if (continua) {
                ultimoAgg = Pref.Update.ultimoAgg.data();
                continua = (ultimoAgg != null);
            }// fine del blocco if

            if (continua) {
                giorniPrevisti = Pref.Update.tempoAgg.intero();
            }// fine del blocco if

            if (continua) {
                giorniPassati = Lib.Data.diff(oggi, ultimoAgg);
                scaduto = (giorniPassati > giorniPrevisti);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return scaduto;
    }


    /**
     * Registra la data odierna come ultimo aggiornamento effettuato.
     * <p/>
     * Recupera l'ultimo aggiornamento dalle preferenze <br>
     * Recupera l'intervallo dalle preferenze <br>
     */
    private void registraUpdate() {
        /* variabili e costanti locali di lavoro */
        Date oggi;

        try { // prova ad eseguire il codice
            oggi = Lib.Data.getCorrente();
            Pref.Update.ultimoAgg.getWrap().setValore(oggi);
            Pref.registra();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     */
    public void fine(boolean aggiornato) {
        /* variabili e costanti locali di lavoro */
        boolean esci = false;
        Dialogo dialogo;
        String fallito;
        String riuscito;

        try { // prova ad eseguire il codice
            fallito = "Non sono riuscito a scaricare gli aggiornamenti";

            riuscito = "Gli aggiornamenti sono stati scaricati e";
            riuscito += "\nsaranno operativi al prossimo riavvio del programma";
            riuscito += "\nVuoi uscire adesso dal programma?";

            if (aggiornato) {
                registraUpdate();
                dialogo = DialogoFactory.annullaConferma("Riavvio");
                dialogo.setMessaggio(riuscito);
                dialogo.avvia();
                if (dialogo.isConfermato()) {
                    Progetto.chiudeProgramma();
                }// fine del blocco if
            } else {
                new MessaggioAvviso(fallito);
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Metodo statico.
     * <p/>
     * Chiamato da Progetto <br>
     * Controlla se l'aggiornamento è attivato <br>
     * Controlla se il periodo di aggiornamento è scaduto <br>
     * Invoca il metodo delegato <br>
     *
     * @return se il programma ha subito aggiornamenti e deve essere riavviato
     *
     * @see it.algos.base.progetto.Progetto#lanciaIstanza(it.algos.base.modulo.Modulo) ;
     */
    public boolean esegue() {
        /* variabili e costanti locali di lavoro */
        boolean esci = false;
        boolean continua;

        try { // prova ad eseguire il codice
            /* Controlla se l'aggiornamento è attivato */
            continua = Pref.Update.usaAgg.is();

            /* Controlla se il periodo di aggiornamento è scaduto */
            if (continua) {
                continua = isAggiornamentoScaduto();
            }// fine del blocco if

            if (continua) {
                esci = esegueBase(false);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return esci;
    }


    /**
     * Metodo statico.
     * <p/>
     * Chiamato da Menu <br>
     * Apre un dialogo per proseguire <br>
     * Invoca il metodo delegato <br>
     *
     * @return se il programma ha subito aggiornamenti e deve essere riavviato
     *
     * @see it.algos.base.progetto.Progetto#lanciaIstanza(it.algos.base.modulo.Modulo) ;
     */
    public boolean dialogoEsegue() {
        /* variabili e costanti locali di lavoro */
        boolean esci = false;
        boolean continua;
        Dialogo dialogo;
        String messaggio;

        try { // prova ad eseguire il codice

            /* apre un dialogo */
            dialogo = DialogoFactory.annullaConferma("Aggiornamenti");
            messaggio = "Vuoi controllare se ci sono aggiornamenti sul server?";
            dialogo.setMessaggio(messaggio);
            dialogo.avvia();
            continua = dialogo.isConfermato();

            if (continua) {
                esci = esegueBase(true);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return esci;
    }


    /**
     * Indirizzo della cartella di aggiornamento sul server FTP.
     *
     * @return cartella base FTP
     */
    private static String getDirFTP() {
        return "/algosUpdate/";
    }


    /**
     * Indirizzo della cartella di aggiornamento sul server FTP.
     *
     * @return cartella sul server
     */
    private String getDirServer() {
        return getDirFTP() + this.getCartella();
    }


    /**
     * Path completo del file nella cartella di aggiornamento sul server FTP.
     *
     * @param nomeFile da elaborare
     *
     * @return Path completo del file
     */
    private String getPathServer(String nomeFile) {
        /* variabili e costanti locali di lavoro */
        String path = "";
        String dir;

        try { // prova ad eseguire il codice
            dir = this.getDirServer();

            if (Lib.Testo.isValida(dir)) {
                path = dir + "/" + nomeFile;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return path;
    }


    /**
     * Path completo del file nella cartella lib locale.
     *
     * @param nomeFile da elaborare
     *
     * @return Path completo del file
     */
    private String getPathLib(String nomeFile) {
        /* variabili e costanti locali di lavoro */
        String path = "";
        String dir;

        try { // prova ad eseguire il codice
            dir = this.getDirLibrerie();

            if (Lib.Testo.isValida(dir)) {
                path = dir + "/" + nomeFile;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return path;
    }


    /**
     * Path completo del file nella cartella tmp locale.
     *
     * @param nomeFile da elaborare
     *
     * @return Path completo del file
     */
    private String getPathTmp(String nomeFile) {
        /* variabili e costanti locali di lavoro */
        String path = "";
        String dir;

        try { // prova ad eseguire il codice
            dir = this.getDirTemporanea();

            if (Lib.Testo.isValida(dir)) {
                path = dir + "/" + nomeFile;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return path;
    }


    /**
     * Indirizzo della cartella locale di lancio del programma in esecuzione.
     *
     * @return cartella principale di lancio
     */
    private String getDir() {
        return System.getProperty("user.dir");
    }


    /**
     * Indirizzo della cartella locale con le librerie di esecuzione.
     *
     * @return cartella lib
     */
    private String getDirLibrerie() {
        return this.getDir() + DIR_LOC_LIB;
    }


    /**
     * Indirizzo della cartella locale con le librerie temporanee di aggiornamento.
     *
     * @return cartella tmp
     */
    private String getDirTemporanea() {
        return Lib.Sist.getDirUpdate();
    }


    private String getServer() {
        return this.server;
    }


    private void setServer(String server) {
        this.server = server;
    }


    private String getUtente() {
        return this.utente;
    }


    private void setUtente(String utente) {
        this.utente = utente;
    }


    private String getPassword() {
        return this.password;
    }


    private void setPassword(String password) {
        this.password = password;
    }


    private String getCartella() {
        return this.cartella;
    }


    public void setCartella(String cartella) {
        this.cartella = cartella;
    }


    private String getKilobyte() {
        /* variabili e costanti locali di lavoro */
        String kilo = "";
        int dim = 0;
        ArrayList<FileWrap> lista;

        try { // prova ad eseguire il codice
            lista = this.getListaFiles();

            if (lista != null && lista.size() > 0) {
                for (FileWrap wrap : lista) {
                    dim += wrap.getDimensioni();
                } // fine del ciclo for-each
                kilo = Lib.Testo.formatNumero(dim);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return kilo;
    }


    public ArrayList<FileWrap> getListaFiles() {
        return listaFiles;
    }


    public void setListaFiles(ArrayList<FileWrap> listaFiles) {
        this.listaFiles = listaFiles;
    }
}// fine della classe
