var EC = protractor.ExpectedConditions;

var waitElementToBeVisible = function(elm){
    //return browser.wait(function(){
    //    return elm.is
    //})
    browser.wait(EC.visibilityOf(elm),15000);
};

exports.waitElementToBeVisible = waitElementToBeVisible;