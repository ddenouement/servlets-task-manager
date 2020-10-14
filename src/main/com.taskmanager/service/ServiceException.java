package service;

/**
 * Custom Exception to throw from Service Layer
 * @Author Yuliia Aleksandrova
 */
public class ServiceException extends Exception{
      ServiceException(String msg){
        super(msg);
    }
}
