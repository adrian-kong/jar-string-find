package dev;

import dev.dump.Dumper;

import java.io.File;

public class Main {

    public static void main(String[] args) throws Exception {
        new Dumper().read(new File(args[0]));
    }
}
