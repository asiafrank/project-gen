package com.asiafrank.tools;

/**
 * @author asiafrank created at 1/5/2017.
 */
public class Main {
    public static void main(String[] args) {
        ProjectInfo info = new ProjectInfo("/Users/asiafrank/workspace",
                "test", "com.asiafrank.test");
        DBParam dbParam = new DBParam(
                "sample",
                "root",
                "root", DB.MYSQL, "", "",
                new String[]{""});

        MvnGenerator mg = new MvnGenerator(info, dbParam);
        mg.exec();
    }
}
