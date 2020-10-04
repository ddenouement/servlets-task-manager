package dao;

public class DaoException extends Exception {
    private String msg;
    public DaoException(String msg){
        super(msg); this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
