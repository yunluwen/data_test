package base;

import java.util.ArrayList;
import java.util.List;

public class ProcessTest {

    public static void main(String[] args) throws Exception {
        List<String> command = new ArrayList<String>();
        command.add("/bin/echo");
        command.add("'aahahaha'");
        command.add(">>");
        command.add("/Users/zhangyunhao/test_file/log.txt");
        //ProcessBuilder processBuilder = new ProcessBuilder(command);

        List<String> command2 = new ArrayList<String>();
        command2.add("/usr/bin/java");
        command2.add("-version");
        ProcessBuilder processBuilder = new ProcessBuilder(command2);

        //processBuilder.command("echo 'aaa'");
        System.out.println(processBuilder.start().waitFor());
    }
}
