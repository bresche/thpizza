package de.thluebeck.pizza;

import de.thluebeck.pizza.controllers.UserController;
import de.thluebeck.pizza.entities.User;
import de.thluebeck.pizza.repositories.UserRepository;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerUnitTest {

    private static UserController userController;
    private static UserRepository mockedUserRepository;
    private static BindingResult mockedBindingResult;
    private static Model mockedModel;

    @BeforeClass
    public static void setUpUserControllerInstance() {
        mockedUserRepository = mock(UserRepository.class);
        mockedBindingResult = mock(BindingResult.class);
        mockedModel = mock(Model.class);
        userController = new UserController(mockedUserRepository);
    }

    @Test
    public void whenCalledIndex_thenCorrect() {
        assertThat(userController.showUserList(mockedModel)).isEqualTo("settings");
    }

    @Test
    public void whenCalledshowSignUpForm_thenCorrect() {
        User user = new User("John", "john@domain.com");

        assertThat(userController.showSignUpForm(user)).isEqualTo("add-user");
    }
    
    @Test
    public void whenCalledaddUserAndValidUser_thenCorrect() {
        User user = new User("John", "john@domain.com");

        when(mockedBindingResult.hasErrors()).thenReturn(false);

        assertThat(userController.addUser(user, mockedBindingResult, mockedModel)).isEqualTo("redirect:/settings");
    }

    @Test
    public void whenCalledaddUserAndInValidUser_thenCorrect() {
        User user = new User("John", "john@domain.com");

        when(mockedBindingResult.hasErrors()).thenReturn(true);

        assertThat(userController.addUser(user, mockedBindingResult, mockedModel)).isEqualTo("add-user");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCalledshowUpdateForm_thenIllegalArgumentException() {
        assertThat(userController.showUpdateForm(0, mockedModel)).isEqualTo("update-user");
    }
    
    @Test
    public void whenCalledupdateUserAndValidUser_thenCorrect() {
        User user = new User("John", "john@domain.com");

        when(mockedBindingResult.hasErrors()).thenReturn(false);

        assertThat(userController.updateUser(1l, user, mockedBindingResult, mockedModel)).isEqualTo("redirect:/settings");
    }

    @Test
    public void whenCalledupdateUserAndInValidUser_thenCorrect() {
        User user = new User("John", "john@domain.com");

        when(mockedBindingResult.hasErrors()).thenReturn(true);

        assertThat(userController.updateUser(1l, user, mockedBindingResult, mockedModel)).isEqualTo("update-user");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void whenCalleddeleteUser_thenIllegalArgumentException() {
        assertThat(userController.deleteUser(1l, mockedModel)).isEqualTo("redirect:/index");
    }
}
