package utils;

public class EmailUtils {
    /**
     *
     * @param email
     * @return
     */
    public static String getEmailtypeBy (String email){
        String emailtype = "其他邮箱用户";
        if(email.contains("@163.com")||email.contains("@126.com")){
            emailtype = "网易邮箱用户";
        }else if(email.contains("@139.com")){
            emailtype = "移动邮箱用户";
        }else if(email.contains("@sohu.com")){
            emailtype = "搜狐邮箱用户";
        }else if(email.contains("@qq.com")){
            emailtype = "qq邮箱用户";
        }else if(email.contains("@189.com")){
            emailtype = "189邮箱用户";
        }else if(email.contains("@tom.com")){
            emailtype = "tom邮箱用户";
        }else if(email.contains("@aliyun.com")){
            emailtype = "阿里邮箱用户";
        }else if(email.contains("@sina.com")){
            emailtype = "新浪邮箱用户";
        }
        return  emailtype;
    }
}
