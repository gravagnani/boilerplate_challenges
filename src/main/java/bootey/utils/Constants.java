package bootey.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String INPUT_FOLDER = System.getProperty("user.dir") + "/src/main/resources/input";
    public static final String OUTPUT_FOLDER = System.getProperty("user.dir") + "/output";

}