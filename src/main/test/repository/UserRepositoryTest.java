package repository;

import dao.DaoException;
import dao.RepositoryFactory;
import dao.UserRepository;
import model.Role;
import model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class UserRepositoryTest {
    @Mock
    DataSource mockDataSource;
    @Mock
    Connection mockConn;
    @Mock
    PreparedStatement mockPreparedStm;
    @Mock
    ResultSet mockResultSet;

    User user;

    @Before
    public void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        user = new User();
        user.setRole(Role.USER.getName());
        user.setEmail("email@gmail.com");
        user.setLogin("simplelogin");
        user.setLastName("MyLastName");
        user.setFirstName("MyFirstName");
        user.setPassword("MyNotEncodedPassword");

        when(mockDataSource.getConnection()).thenReturn(mockConn);
        when(mockDataSource.getConnection(anyString(), anyString()))
                .thenReturn(mockConn);
        doNothing().when(mockConn).commit();
        when(mockConn.prepareStatement(anyString())).thenReturn(
                mockPreparedStm);
        doNothing().when(mockPreparedStm).setInt(anyInt(), anyInt());
        doNothing().when(mockPreparedStm).setString(anyInt(), anyString());
        when(mockPreparedStm.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
    }

    @Test(expected = DaoException.class)
    public void givenAlreadyUsedLogin_whenTryRegister_shouldThrowDaoExc() throws DaoException {
        RepositoryFactory factory = new RepositoryFactory(mockDataSource);
        UserRepository repository = factory.userRepository();
        repository.register(user);
    }


}
