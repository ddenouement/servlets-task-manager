package commands.util;


import sun.security.validator.ValidatorException;

import javax.servlet.http.HttpServletRequest;
/**
 * Util class to get int or String parameter from HttpRequest
 * @Author Yuliia Aleksandrova
 */
public class ParameterGetter {
    public static String getStringParam(HttpServletRequest request, String name) throws ValidatorException {
        String field = request.getParameter(name);
        if(field == null || field.isEmpty()){
            throw new ValidatorException("No value");
        }
        return field;
    }
    public static int getIntParam(HttpServletRequest request, String name) throws ValidatorException {
        String field = request.getParameter(name);
        if(field == null || field.isEmpty()){
            throw new ValidatorException("No value");
        }
        int res = 0;
        try{
            res =  Integer.parseInt(field);
        }
        catch (Exception a){
            throw new ValidatorException("No value");
        }
        return res;
    }
}
