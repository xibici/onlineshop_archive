package com.xjh.utils;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016102400753044";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCM7Wmf4rchVzZxS+314yrEAt9AIcr4HBhoFZZa1JK32gy7ZXi+Auu7kKqiglCbT5f7pQ8r6LE7FPBx8eKH74kaAiKbmFRS1K/sSn91B8SXuLM7/KTy3kvpi6t3cLR4sQLSHAbj56Ohn9pG3v7/KRVMK/BJZKX8PMO6bs988HAdWKIwfjT+zuZ8YwssLvNuk9SlukagyRPGh5xZhCSwpgGUcAGDk7qbfEoyidSMbxrRgJNyz2YKUTdTTzSjKpgH6S8pEy7lYhM1aJ3oK5bQPUajRMmnkMRgT1RxEXgv7rtDO122HknvsPe5fJHCsEp0gri625DnyR2BbNfW5vjYJI9TAgMBAAECggEAZieKkz1yZhaj/kh9++lpjKBQWFqmmGc1Qe20Ca9hkyfaZtTNS18upc3eAuLXr11o3B7v+nI4Vo19Jfuiq8r05Qd7MrKfHVPTGkN+VMUGurxGIEuo/ewptudpqbDnAT2+v7NVQ0scEAKh5RGsYiXTrF2FVo5Ay7D4aGH+waBAhOQxelAhxccCWR5Uo0OAr6x7PvtCQfi0ghcqy3e2wtBLOkWcSFArRYQLhMMlNs7k8K8Au1HQxQI5U4z/2/c5q/c2hxZwZX1btlwsdSVRk/DOjGni33oaO4QLHDDAv7xaihdZ0zX7uYj0KTjrQfGNi6Ybs4c2IGGtX6/sDdUoY1QtMQKBgQDAEfP/dyMmceRFkrFkI2lImbE0pHXooxtwv+7We6sBHpzKB8B/vXGRw4EFVUcZOzOytj4Jpj8el4G9zXI931jho9tMneNavlwxmiZnp582ZMhP24zGf8MEQaSJuGeubt5XeWmA38jzOa0anXaI32a0fEgo0wIxCVBytu3D5o3JdwKBgQC71ac2DDsibcvVOrCoE2LKUqL9Myfyo1JoOG5rMiEne5utRbgdo+aGE5VAlcsuIhX9o+1j5f/6ScNhKqyKugNiGpyq+ticJJ9GJ+EsIlfCNTGQioPTgzOyUFzQX41gBPMG+k53/ugKb8+HCab1GobsRuxrppaLYJkX1FGtx4VgBQKBgQCVE5oxl5PlvBJ0WaZr1fxayEe9V0TZGvgqbB6BxRz2Un9ZY+jqfOzQOVFEBWuYztWSiVbfgHSPVwLxgauBEIDZKagF8KpNfWDrcv0KCRVPT6wWJ489Zl7ER/rglguLXSwf8vGLgKZk+kg8VylwlQ0PW0NaxGR4EQWq9AbZvXIhgwKBgG7q326/EjxmcbNckOzvZarr2WuDRde2MfdxO2L5mzJhf7B2Nx7ppjsOXVhiHDUF086PeqM/MkVuQX2jYXgaF91asaEVt8ucpKEc847ISOJZ3wbnhKjmFST655P0mNtWkNxR+vxI/mULWZT4UirymzHzP7DiEowDYuP7cLAZ3dyxAoGAeAguEEUjM2Hy2hJtXa3Fw+5Bn0tQdVNYG/WRTq+RwHivuu2FKH9qOh4SC9h0i1RnDQM1We05s2o3XG7nzavmIG25pi9fnK+vXRZPFdQO6hoj0+VdVg9uZ19Qh6sa/n2YD240I3aXTXLQDXeO3swY1ZOEKJ1yiQomEx06pL4KBPE=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjUYfjr6ypx8AJeWtkFFyvi5yShwyadZ45e5hr2rUZYTG3fPXpfbe1vcuoI1jKFdAo3eK1CSE9Gz8tlBLs+h1JkRJ7O+DosKQgw6Aexj27I7YrCrLFTo33qqtpuV3TWECnRpMZ7Aoo27f+NW/ztdANoHfdCCeAwXcjq+RFDbzY3W9jD4su1BSNLPXXOcc3xjIyVSmCYCKEDpEHDTLXqVja7fUJ/0PlsbXd8KR71lIUMLzA35ylD/UAcAn2opKlHYTMf2c2l4ktV1bjiogVmegGoznxGBHfqMvshnP03YNACr30DaY0zan6z3JE3Ww2UhqWPqLX5lC61CFu4Lf3cqXyQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost/xjhshop/notify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://localhost/xjhshop/return_url.jsp";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

/*package cn.taosir.demo;

public class AlipayConfig {
    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数
    public static String return_url = "http://localhost:8080/success.jsp";
    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://localhost:8080/notify";
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "";
    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "";
    // 签名方式
    public static String sign_type = "RSA2";
    // 字符编码格式
    public static String charset = "utf-8";
    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
}*/