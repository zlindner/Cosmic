package zachy.cosmic.core.util;

public class EnergyUtils {

    public static int getSink(double input) {
        if (input == 8) {
            return 0;
        } else if (input == 32) {
            return 1;
        } else if (input == 128) {
            return 2;
        } else if (input == 512) {
            return 3;
        } else if (input == 2048) {
            return 4;
        } else if (input == 8192) {
            return 5;
        }

        return 0;
    }
}
