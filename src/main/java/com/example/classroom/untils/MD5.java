package com.example.classroom.untils;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class MD5 {
    private static final String slat = "&%d13wa**&&%%$$#@";

    public  String getMD5(String str) {
        String base = str + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}

