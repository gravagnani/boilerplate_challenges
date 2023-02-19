package bootey.io;

import lombok.extern.log4j.Log4j2;
import bootey.dto.ChallengeModel;
import bootey.dto.Delivery;
import bootey.dto.Pizza;
import bootey.utils.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Log4j2
public class DataParser {

    private DataParser() {
    }

    public static ChallengeModel fromFile(File file) {
        ChallengeModel challenge = new ChallengeModel();
        String data;
        String[] splitData;

        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //TODO: decide how to scan and save data

        // first line
        data = scanner.nextLine();
        splitData = data.split(" ");
        challenge.setNPizzas(Integer.parseInt(splitData[0]));
        challenge.setNT2(Integer.parseInt(splitData[1]));
        challenge.setNT3(Integer.parseInt(splitData[2]));
        challenge.setNT4(Integer.parseInt(splitData[3]));

        // next 'till end

        challenge.setListDelivers(new ArrayList<>());

        List<Pizza> listPizzas = new ArrayList<>();
        int libIxd = 0;
        while (scanner.hasNextLine()) {
            // first line
            data = scanner.nextLine();
            splitData = data.split(" ");
            int n = Integer.parseInt(splitData[0]);
            String[] ing = new String[splitData.length - 1];


            for (int i = 1; i < splitData.length; i++) {
                ing[i - 1] = splitData[i];
            }

            Pizza p = new Pizza(libIxd, n, ing);

            listPizzas.add(p);
            libIxd++;
        }
        challenge.setListPizzas(listPizzas);


        scanner.close();
        return challenge;
    }

    public static void toFile(ChallengeModel challenge, String fileName) throws IOException {

        String prefix = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis()));
        String outputFilename = prefix + "_" + fileName;

        try (FileWriter writer = new FileWriter(new File(Constants.OUTPUT_FOLDER, outputFilename))) {

            //TODO: decide how to return output

            writer.write("" + challenge.getListDelivers().size() + "\n");
            for (Delivery d : challenge.getListDelivers()) {
                writer.write("" + d.getNDeliveredPizzas() + " ");
                StringBuilder s = new StringBuilder();
                for (Pizza p : d.getDeliveredPizzas()) {
                    s.append(p.getId()).append(" ");
                }
                writer.write(s + "\n");
            }

        } catch (IOException e) {
            log.error("error: ", e);
        }
    }
}
