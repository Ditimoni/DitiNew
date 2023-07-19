package stepdefinitions.ui.diti;

import pagefactory.CommonMethodUI;
import pagefactory.ReusableMethodUI;
import io.cucumber.java.en.And;
import pagefactory.pages.LoginPage;
import utils.BaseClass;

import java.io.FileNotFoundException;
import java.io.IOException;

public class CommonStepDef {


    private LoginPage lp = new LoginPage(BaseClass.getDriver());
    private ReusableMethodUI rm = new ReusableMethodUI(BaseClass.getDriver());
    private CommonMethodUI cm = new CommonMethodUI(BaseClass.getDriver());

    public CommonStepDef() throws IOException {
    }

    @And("I Login As {string} User on {string} Brand")
    public void loginWithDiffUser(String usertype, String brand) throws FileNotFoundException {
        lp.verifyLoginFunctionality(usertype, brand);
    }
}
