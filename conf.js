exports.config={
    seleniumAddress:'http://localhost:4444/wd/hub',
    specs: ['spec.js'],
    params:{
        url : 'https://www.etsy.com/',
        //url :'https://cb62t.am.health.ge.com/cbwui/'
    },



    capabilities: {
        browserName: 'chrome',
        acceptSslCerts: true,
        shardTestFiles: false,
        maxInstances: 1
    },

    onPrepare: function(){
        browser.ignoreSynchronization = true;
        browser.driver.manage().window().maximize();
    },

    jasmineNodeOpts: {
        defaultTimeoutInterval: 2500000
    }

//    capabilities: {
//browserName: 'firefox',
//   //name: 'Unnamed Job',
//   //logName: 'Chrome - English',
//   //count: 1,
//   //shardTestFiles: false,
//  // maxInstances: 1,
//        specs: ['spec.js'],
//   //exclude: ['spec/doNotRunInChromeSpec.js'],
//   seleniumAddress: 'http://localhost:4444/wd/hub'
// }

};