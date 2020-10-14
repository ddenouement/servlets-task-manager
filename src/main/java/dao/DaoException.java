package dao;
/**
 * Custom exception to throw from Repository Layer
 * @Author Yuliia Aleksandrova
 */
public class DaoException extends Exception {
    private String msg;
    DaoException(String msg){
        super(msg); this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
