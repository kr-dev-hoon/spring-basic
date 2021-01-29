package dev.hoon.basic.global.util;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MobileNumberUtil {

    private static final Logger logger = LoggerFactory.getLogger(MobileNumberUtil.class);

    public static boolean validPhoneNumber(int countryCode, String phoneNumber) {

        logger.debug("Check Phone Number :: countryCode : {}, phoneNumber : {}", countryCode, phoneNumber);

        PhoneNumberUtil util = PhoneNumberUtils.getInstance();
        String regionCode = util.getRegionCodeForCountryCode(countryCode);

        return util.isPossibleNumber(phoneNumber, regionCode);
    }

    private static class PhoneNumberUtils {

        private static final PhoneNumberUtil util = PhoneNumberUtil.getInstance();

        public static PhoneNumberUtil getInstance() {

            return util;
        }
    }
}
