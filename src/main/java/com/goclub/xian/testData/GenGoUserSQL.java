package com.goclub.xian.testData;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class GenGoUserSQL {
    static String[] surnames = {"王", "李", "张", "刘", "陈", "杨", "赵", "黄", "周", "吴"};
    static String[] names = {"伟", "芳", "娜", "敏", "静", "秀英", "丽", "强", "磊", "军"};
    static String[] genders = {"男", "女"};
    static String[] rankLevels = {"25级", "20级", "15级", "10级", "5级", "1级"};
    static String[] roles = {"user", "admin", "superadmin"};
    static String[] districts = {"未央区", "碑林区", "雁塔区", "新城区", "莲湖区", "灞桥区"};
    static String[] schoolDistricts = {"未央区", "碑林区", "雁塔区"};
    static Random rand = new Random();

    // 随机生日 1995-01-01 ~ 2018-12-31
    static String randomBirthDate() {
        long start = 788918400000L;  // 1995-01-01
        long end = 1546185600000L;   // 2018-12-31
        long millis = start + (long) (rand.nextDouble() * (end - start));
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(millis));
    }

    public static void main(String[] args) throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter("insert_go_user.sql"));
        for (int i = 1; i <= 6000; i++) {
            String surname = surnames[rand.nextInt(surnames.length)];
            String name = names[rand.nextInt(names.length)];
            String realName = surname + name;
            String uuid = UUID.randomUUID().toString();
            String username = "user" + i;
            String gender = genders[rand.nextInt(genders.length)];
            String birthDate = randomBirthDate();
            String idCard = "6101" + String.format("%014d", i); // 18位
            String phone = "13" + (rand.nextInt(900000000) + 100000000);
            String email = username + "@test.com";
            String password = "123456"; // 可替换为加密
            String province = "陕西";
            String city = "西安";
            String district = districts[rand.nextInt(districts.length)];
            String schoolDistrict = schoolDistricts[rand.nextInt(schoolDistricts.length)];
            String rankLevel = rankLevels[rand.nextInt(rankLevels.length)];
            int danLevel = rand.nextInt(5) + 1;
            String role = roles[rand.nextInt(roles.length)];
            String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            String sql = String.format(
                    "INSERT INTO `go_club`.`user` " +
                            "(uuid, username, real_name, gender, birth_date, id_card, phone, email, password, province, city, district, school_district, rank_level, dan_level, role, last_school_district_update, created_at, updated_at) " +
                            "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', %d, '%s', '%s', '%s', '%s');",
                    uuid, username, realName, gender, birthDate, idCard, phone, email, password, province, city, district, schoolDistrict, rankLevel, danLevel, role, now, now, now
            );
            System.out.println(sql);
        }
        System.out.println("SQL已生成");
    }
}
