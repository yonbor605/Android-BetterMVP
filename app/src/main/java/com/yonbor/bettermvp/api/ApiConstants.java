package com.yonbor.bettermvp.api;

public class ApiConstants {

    public static final String BASEURL = "http://www.baidu.com/";


    /**
     * 获取对应的host
     *
     * @param hostType host类型
     * @return host
     */
    public static String getHost(int hostType) {
        String host;
        switch (hostType) {
            case HostType.HOME_NEW_LIST:
                host = "http://v.juhe.cn/toutiao/";
                break;
            case HostType.PICTURE_NEW_LIST:
                host = BASEURL;
                break;

            default:
                host = "";
                break;
        }
        return host;
    }
}
