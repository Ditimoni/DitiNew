var helper = require('./helper');

describe('Protractor demo', function()
{
it('should have a title', function()
    {
       // browser.get('https://angularjs.org');
        browser.get(browser.params.url);
       // browser.driver.get('https://cb62t.am.health.ge.com/cbwui');
        //browser.sleep(50000);
        var title = 'Etsy.com | Shop for anything from creative people everywhere';
        //expect(browser.getTitle()).toContain('Centricity');
        expect(browser.getTitle()).toEqual(title);
    });

    it('registration to the site',function(){
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
});