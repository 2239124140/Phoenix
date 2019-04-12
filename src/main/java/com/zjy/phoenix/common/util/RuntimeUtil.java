package com.zjy.phoenix.common.util;


import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RuntimeUtil {

    private static Log logger = LogFactory.getLog(RuntimeUtil.class);

    public static void filterInfo(List<String> data, Map<String, String> result, List<String> filter) {
        for (String info : data) {
            String[] kv = info.split("=");
            if (kv.length == 2) {
                if (filter.contains(kv[0])) {
                    result.put(kv[0], kv[1]);
                }
            }
        }
    }

    public static Map<String, Object> executeCommond(String cmd, String[] envp) {
        logger.debug(cmd);
        Runtime runTime = Runtime.getRuntime();
        try {
            final Process pro = runTime.exec(cmd, envp);
            Thread shutdownHook = new Thread() {
                @Override
                public void run() {
                    pro.destroy();
                };
            };
            runTime.addShutdownHook(shutdownHook);
            List<String> infos = IOUtils.readLines(pro.getInputStream());
            if(infos!=null&&infos.size()>0){
                displayList(infos);
            }
            List<String> errors = IOUtils.readLines(pro.getErrorStream());
            if (errors.size() > 0) {
                logger.error("cmd:" + cmd);
                logger.error("errors:");
                displayList(errors);
            }
            int code = pro.waitFor();
            pro.destroy();
            runTime.removeShutdownHook(shutdownHook);
            Map<String, Object> result = new HashMap<String, Object>(3);
            result.put("code", code);
            result.put("exitValue", pro.exitValue());
            result.put("infos", infos);
            result.put("errors", errors);
            return result;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static List<String> excuteCmd(String cmd) {
        Map<String, Object> result = executeCommond(cmd, null);
        if (result != null) {
            int code = (Integer) result.get("code");
            int exitValue = (Integer) result.get("exitValue");
            if (code == 0) {
                if (exitValue == 0) {
                    return (List<String>) result.get("infos");
                }
            }
            logger.warn("code=" + code + " ; exitValue=" + exitValue);
        }
        return null;
    }

    public static boolean excuteCmdWithNoInput(String cmd, String[] envp) {
        Map<String, Object> result = executeCommond(cmd, envp);
        if (result != null) {
            int code = (Integer) result.get("code");
            int exitValue = (Integer) result.get("exitValue");
            if (code == 0) {
                if (exitValue == 0) {
                    return true;
                }
            }
            logger.warn("code=" + code + " ; exitValue=" + exitValue + ",cmd=" + cmd);
        }
        return false;
    }

    public static boolean excuteCmdWithNoInput(String cmd) {
        logger.info("cmd=" + cmd);
        return excuteCmdWithNoInput(cmd, null);
    }

    public static void displayList(List<String> list) {
        for (String str : list) {
            logger.error(str);
        }
    }

    public static boolean excuteCmdWithNoInputOFPDF(String cmd, String[] envp) {
        Map<String, Object> result = executeCommondWithNoInputOFPDF(cmd, envp);
        if (result != null) {
            int code = (Integer) result.get("code");
            int exitValue = (Integer) result.get("exitValue");
            if (code == 0) {
                if (exitValue == 0) {
                    return true;
                }
            }
            logger.warn("code=" + code + " ; exitValue=" + exitValue);
        }
        return false;
    }

    public static Map<String, Object> executeCommondWithNoInputOFPDF(String cmd, String[] envp) {

        Runtime runTime = Runtime.getRuntime();
        try {
            final Process pro = runTime.exec(cmd, envp);
            Thread shutdownHook = new Thread() {
                public void run() {
                    pro.destroy();
                };
            };
            runTime.addShutdownHook(shutdownHook);

//			logger.info("step 1");
            // List<String> infos = IOUtils.readLines(pro.getInputStream());

//			logger.info("step 2");
            // List<String> errors = IOUtils.readLines(pro.getErrorStream());

            InputStreamProccessor p = new InputStreamProccessor(pro);

            p.start();

//			logger.info("step 3");
            int code = pro.waitFor();

//			logger.info("step 4");
            pro.destroy();
            p.stop();

            runTime.removeShutdownHook(shutdownHook);

            Map<String, Object> result = new HashMap<String, Object>(3);
            result.put("code", code);
            result.put("exitValue", pro.exitValue());
            // result.put("infos", infos);
            // result.put("errors", errors);
            return result;
        } catch (Exception e) {
            e.fillInStackTrace();
            return null;
        }
    }

    static class InputStreamProccessor {

        InputStreamProccessor(Process pro) {
            this.pro = pro;
        }

        Process pro;

        Thread info;
        Thread error;

        void start() {

            info = new Thread() {

                @Override
                public void run() {
                    try {
                        List<String> infos = IOUtils.readLines(pro.getInputStream());
                        if(infos!=null&&infos.size()>0){
                            displayList(infos);
                        }
//						IOUtils.readLines(pro.getInputStream());
//
//						logger.info("finish input");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };

            error = new Thread() {

                @Override
                public void run() {
                    try {
                        List<String> errors = IOUtils.readLines(pro.getErrorStream());
                        if (errors.size() > 0) {
//							logger.error("cmd:" + cmd);
                            logger.error("errors:");
                            displayList(errors);
                        }
//						IOUtils.readLines(pro.getErrorStream());
//
//						logger.info("finish error");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };

            info.setDaemon(true);
            error.setDaemon(true);

            info.start();
            error.start();
        }

        void stop() {
            if (info != null && !info.isInterrupted()) {
                info.interrupt();
            }

            if (error != null && !error.isInterrupted()) {
                error.interrupt();
            }
        }
    }
}
