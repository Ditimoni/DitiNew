var helper = require('./helper');

describe('Protractor demo', function()
{
    beforeEach(function(){
        browser.get(browser.params.url);
    });

    afterEach(function(){
        browser.manage().deleteAllCookies();
    });

it('should have a title', function()
    {
       // browser.driver.get('https://cb62t.am.health.ge.com/cbwui');
        //browser.sleep(50000);
        var title = 'Etsy.com | Shop for anything from creative people everywhere';
        //expect(browser.getTitle()).toContain('Centricity');
        expect(browser.getTitle()).toEqual(title);
    });

    it('error while sign in to the site',function(){
        var signinButton = element(by.id('sign-in'));
        var usernameField = element(by.id('username-existing'));
        var passwordField = element(by.id('password-existing'));
        var signinInLoginForm = element(by.id('signin-button'));
        var errorUserName = element(by.id('username-existing-error'));
        signinButton.click();
        helper.waitElementToBeVisible(usernameField);
        usernameField.sendKeys('ditimoni');
        passwordField.sendKeys('gohain');
        signinInLoginForm.click();
       // expect(errorUserName.getText()).toEqual('Username is invalid.');
        expect(errorUserName.getText()).toBe('Username is invalid.');
});
    it('Registration to the site', function(){
        var registrationBtn = element(by.id('register'))
        var firstName =element(by.id('first-name'))
        var email = element(by.id('email'))
        var password = element(by.id('password'))
        var confirmPassword = element(by.id('password-repeat'))
        registrationBtn.click()
        helper.waitElementToBeVisible(firstName)
        firstName.sendKeys('ditimoni')
        email.sendKeys('diti@gmail.com')
        password.sendKeys('password')
        confirmPassword.sendKeys('password')
    });
});