package wh.future.framework.desensitization;

import cn.hutool.core.util.StrUtil;

/**
 * @author Administrator
 */
public class DesensitizationUtil {

    public static String str(CharSequence cs) {
        return null == cs ? null : cs.toString();
    }

    public static String email(String email) {
        if (StrUtil.isBlank(email)) {
            return "";
        } else {
            int index = StrUtil.indexOf(email, '@');
            return index <= 1 ? email : StrUtil.hide(email, 1, index);
        }
    }

    public static String idCardNum(String idCardNum, int front, int end) {
        if (StrUtil.isBlank(idCardNum)) {
            return "";
        } else if (front + end > idCardNum.length()) {
            return "";
        } else {
            return front >= 0 && end >= 0 ? StrUtil.hide(idCardNum, front, idCardNum.length() - end) : "";
        }
    }


    public static String password(String password) {
        return StrUtil.isBlank(password) ? "" : StrUtil.repeat('*', password.length());
    }

    public static String carLicense(String carLicense) {
        if (StrUtil.isBlank(carLicense)) {
            return "";
        } else {
            if (carLicense.length() == 7) {
                carLicense = StrUtil.hide(carLicense, 3, 6);
            } else if (carLicense.length() == 8) {
                carLicense = StrUtil.hide(carLicense, 3, 7);
            }

            return carLicense;
        }
    }

    public static String bankCard(String bankCardNo) {
        if (StrUtil.isBlank(bankCardNo)) {
            return bankCardNo;
        } else {
            bankCardNo = StrUtil.cleanBlank(bankCardNo);
            if (bankCardNo.length() < 9) {
                return bankCardNo;
            } else {
                int length = bankCardNo.length();
                int endLength = length % 4 == 0 ? 4 : length % 4;
                int midLength = length - 4 - endLength;
                StringBuilder buf = new StringBuilder();
                buf.append(bankCardNo, 0, 4);

                for(int i = 0; i < midLength; ++i) {
                    if (i % 4 == 0) {
                        buf.append(' ');
                    }

                    buf.append('*');
                }

                buf.append(' ').append(bankCardNo, length - endLength, length);
                return buf.toString();
            }
        }
    }

    public static String mobilePhone(String num) {
        return StrUtil.isBlank(num) ? "" : StrUtil.hide(num, 3, num.length() - 4);
    }

    public static String address(String address, int sensitiveSize) {
        if (StrUtil.isBlank(address)) {
            return "";
        } else {
            int length = address.length();
            return StrUtil.hide(address, length - sensitiveSize, length);
        }
    }

    public static String doNothing(String val){
        return val == null ? "" : val;
    }

    public static String replace(CharSequence str, int startInclude, int endExclude, char replacedChar) {
        if (StrUtil.isEmpty(str)) {
            return str(str);
        } else {
            String originalStr = str(str);
            int[] strCodePoints = originalStr.codePoints().toArray();
            int strLength = strCodePoints.length;
            if (startInclude > strLength) {
                return originalStr;
            } else {
                if (endExclude > strLength) {
                    endExclude = strLength;
                }
                if (startInclude > endExclude) {
                    return originalStr;
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < strLength; ++i) {
                        if (i >= startInclude && i < endExclude) {
                            stringBuilder.append(replacedChar);
                        } else {
                            stringBuilder.append(new String(strCodePoints, i, 1));
                        }
                    }

                    return stringBuilder.toString();
                }
            }
        }
    }

}
