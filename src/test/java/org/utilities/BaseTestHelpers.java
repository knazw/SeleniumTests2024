package org.utilities;

public class BaseTestHelpers {

    public static String getBrowser(String input) {
        // [number] chrome, [number] firefox
        String[] array = input.split("] ");
        if(array.length == 2) {
            return array[1];
        }
        return null;
    }
}
