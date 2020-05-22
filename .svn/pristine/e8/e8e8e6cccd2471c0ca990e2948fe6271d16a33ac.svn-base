package tms.network;

import tms.util.InvalidNetworkException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class NetworkInitialiser {
    public static final String LINE_INFO_SEPARATOR = ":";
    public static final String LINE_LIST_SEPARATOR = ";";

    public NetworkInitialiser() {
        // empty
    }

    public static Network loadNetwork(String filename)
            throws IOException,
            InvalidNetworkException {
        Network network = new Network();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
        String currentLine = bufferedReader.readLine();
        int currentLineNumber = 1;
        int numIntersections = 0;
        int numRoutes = 0;
        while (currentLine != null) {
            if (currentLine.startsWith(";")) {
                currentLine = bufferedReader.readLine();
                continue;
            }

            switch(currentLineNumber) {
                case 1:
                    numIntersections = Integer.parseInt(currentLine);
                    break;
                case 2:
                    numRoutes = Integer.parseInt(currentLine);
                    break;
                case 3:
                    network.setYellowTime(Integer.parseInt(currentLine));
                    break;
                case 4:
                    for (int i = 0; i < numIntersections; i++) {
                        if (currentLine.contains(":")) {
                            String[] rows = currentLine.split(":");
                        }
                        network.createIntersection(currentLine);

                        //Add parse here

                        currentLine = bufferedReader.readLine();
                    }
            }

            currentLineNumber++;
            currentLine = bufferedReader.readLine();
        }

        // Parse the data
        rawMatrix = rawMatrix.substring(1, rawMatrix.length() - 1);
        String[] rows = rawMatrix.split(";");
        int rowCount = rows.length;

        int columnCount = rows[0].split(",").length;

        Matrix matrix = new Matrix(rowCount, columnCount);

        for (int i = 0; i < rowCount; i++) {
            String row = rows[i];
            row = row.substring(1, row.length() - 1);
            String[] columns = row.split(",");
            for (int j = 0; j < columnCount; j++) {
                String column = columns[j];
                matrix.set(i, j, Double.parseDouble(column));
            }
        }
        bufferedReader.close();
        return network;
    }
}
