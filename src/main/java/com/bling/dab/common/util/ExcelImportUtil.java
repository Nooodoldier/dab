package com.bling.dab.common.util;

/**
 * @author: hxp
 * @date: 2019/3/31 10:59
 * @description:
 */
public class ExcelImportUtil {

    /** @描述：是否是2003的excel，返回true是2003 */
    public static boolean isExcel2003(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /**@描述：是否是2007的excel，返回true是2007 */
    public static boolean isExcel2007(String filePath)  {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

}
