package process;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * mysql
 * 执行shell,获取执行结果
 */
public class ProcessResult {

    private static Logger logger = LoggerFactory.getLogger(ProcessResult.class);

    /**
     * 定时执行导入脚本
     */


    /**
     * 定时执行数据merge
     */


    /**
     * @param command 执行命令
     * @return 执行结果
     * @throws Exception
     */
    private static String getDataByShellCMD(String[] command) throws Exception{
        Process process = null;
        BufferedReader in = null;
        try{
            process = Runtime.getRuntime().exec(command);
            int iWaitFor = process.waitFor();
            if (iWaitFor != 0) {
                logger.error("任务执行出错。。。");
            }
            in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            return in.readLine();   //只要一行
        } catch (Exception e) {
            return null;
        } finally {
            if (process != null) {
                process.destroy();
            }
            if (in!=null){
                in.close();
            }
        }
    }

    public static void main(String[] args) throws Exception{
        String[] command = {"/bin/ls","/"};
        System.out.println(getDataByShellCMD(command));
    }
}
