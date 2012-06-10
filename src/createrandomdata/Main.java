/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package createrandomdata;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

/**
 *
 * @author Bryan E. Smith - bryanesmith@gmail.com
 */
public class Main {

    private static final long B = 1;
    private static final long KB = 1024 * B;
    private static final long MB = 1024 * KB;
    private static final long GB = 1024 * MB;
    /**
     * The number of bytes that should be written at a time. Keep low for lower memory requirements, high to potentially write faster.
     */
    private static int bytesToWriteAtATime = 1024;
    /**
     * If testing, do not exit JVM.
     */
    private static boolean testing = false;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String filePath = null;
        File file = null;
        long size = MB; // Default size, might be replaced by user-defined in optional param

        // If no parameters, error message about requiring file
        if (args.length == 0) {
            printErrorMessage("Output file name (or path) required.");
            if (isTesting()) {
                return;
            } else {
                System.exit(1);
            }
        }
        
        // Quickly check if single parameter is help
        if (args.length == 1 && (args[0].equals("-h") || args[0].equals("--help"))) {
            printUsage();
            if (isTesting()) {
                return;
            } else {
                System.exit(0);
            }
        }

        try {
            filePath = args[args.length - 1];
            file = new File(filePath);

            if (file.exists()) {
                throw new Exception("File already exists, will not clobber existing file.");
            }

            // Copy over optional parameters for easier manipulation
            String[] optionalParams = new String[args.length - 1];
            for (int i = 0; i < args.length - 1; i++) {
                optionalParams[i] = args[i];
            }

            for (int i = 0; i < optionalParams.length; i++) {
                /**
                 * Help
                 */
                if (optionalParams[i].trim().equals("-h") || optionalParams[i].trim().equals("--help")) {
                    printUsage();
                    if (isTesting()) {
                        return;
                    } else {
                        System.exit(0);
                    }
                } 
                /**
                 * Size
                 */
                else if (optionalParams[i].trim().equals("-s") || optionalParams[i].trim().equals("--size")) {
                    i++; // Need to get the value
                    if (i >= optionalParams.length) {
                        throw new Exception("Expecting size following -s parameter, not found!");
                    }

                    String sizeString = optionalParams[i];

                    try {
                        if (sizeString.endsWith("GB")) {
                            double multiple = Double.parseDouble(sizeString.substring(0, sizeString.length() - 2));
                            size = (long)(multiple * GB);
                        } else if (sizeString.endsWith("MB")) {
                            double multiple = Double.parseDouble(sizeString.substring(0, sizeString.length() - 2));
                            size = (long)(multiple * MB);
                        } else if (sizeString.endsWith("KB")) {
                            double multiple = Double.parseDouble(sizeString.substring(0, sizeString.length() - 2));
                            size = (long)(multiple * KB);
                        } else if (sizeString.endsWith("B")) {
                            double multiple = Double.parseDouble(sizeString.substring(0, sizeString.length() - 1));
                            size = (long)(multiple * B);
                        } else {
                            long multiple = Long.parseLong(sizeString);
                            size = multiple * B;
                        }
                    } catch (NumberFormatException nex) {
                        throw new Exception("Cannot parse size " + sizeString);
                    }
                } 
                /**
                 * Error, unrecognized option
                 */
                else {
                    printErrorMessage("Unrecognized parameter " + optionalParams[i]);
                    if (isTesting()) {
                        return;
                    } else {
                        System.exit(1);
                    }
                }
            }

            createFileWithRandomData(file, size);
            if (isTesting()) {
                return;
            } else {
                System.exit(0);
            }

        } catch (Exception ex) {
            printErrorMessage("A "+ex.getClass().getSimpleName()+" occurred with the following message: "+ex.getMessage());
            System.err.println("Details:");
            ex.printStackTrace(System.err);
            System.err.flush();
            
            if (isTesting()) {
                return;
            } else {
                System.exit(1);
            }
        }
    }

    private static void printErrorMessage(String errorMsg) {
        System.err.println("randomf: " + errorMsg);
        System.err.println("Try `randomf --help' for more information.");
    }

    private static void printUsage() {
        System.err.println("");
        System.err.println("USAGE: randomf [OPTION] DEST");
        System.err.println("Create a file of arbitrary size with random data. By default, the file is 1MB in size. To specify the size, see --size optional argument.");
        System.err.println("");
        System.err.println("OPTIONAL ARGUMENTS:");
        System.err.println("  -h, --help                Print this menu. Will not create any file if specified.");
        System.err.println("  -s, --size                Size of output file.");
        System.err.println("                            E.g., 512 represents 512 bytes");
        System.err.println("                            Optional units: B (bytes), KB, MB, GB");
        System.err.println("                            Default value is 1MB.");
        
    }

    private static void createFileWithRandomData(final File file, final long size) throws Exception {
        Random r = new Random();
        BufferedOutputStream writer = null;
        try {
            writer = new BufferedOutputStream(new FileOutputStream(file));

            int bytesToWriteThisIteration;
            long bytesAlreadyWritten = 0;
            byte[] nextBytes;
            for (int i = 0; i < size; i += bytesToWriteAtATime) {

                // Assume writting a full array of bytes
                bytesToWriteThisIteration = bytesToWriteAtATime;

                // Make sure we aren't writting to many bytes
                if (bytesToWriteThisIteration > size - bytesAlreadyWritten) {
                    // Safe to downcast, should never be greater than the integer bytesToWriteThisIteration
                    bytesToWriteThisIteration = (int) (size - bytesAlreadyWritten);
                }

                nextBytes = new byte[bytesToWriteThisIteration];
                r.nextBytes(nextBytes);

                writer.write(nextBytes);

                bytesAlreadyWritten += bytesToWriteThisIteration;
            }
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }

    public static void setTesting(boolean testing) {
        Main.testing = testing;
    }

    public static boolean isTesting() {
        return testing;
    }
}
