applecom_functional_tests
========================

<strong><p style="font-size:24px;">Selenium Based Functional Tests</p></strong>

In an effort to reduce time on repeated testing, we are experimenting with automation on some of the elements that do not (or rarely) change.
This includes Global Nav, Global Footer, Product Nav, Directory Nav, 404 & SEO Checks for all pages under test. 

<strong>Linux/Unix based install</strong>

Open terminal create a /test folder in your home drive and cd there
	mkdir test
	cd test

You should be in /Users/[username]/test

Run command:

    git init
    git clone https://interactive-git.apple.com/rashichopra/applecom_functional_tests
    cd applecom_functional_tests
    git tag (this will show you the latest version)
    git checkout -b (whatever the latest tag is)
    	*note: if you already have a clone, run git fetch --tags to get the latest tags
    
You should now have the latest version of applecom_functional_tests

<strong>To Run the tests </strong>

To run the tests, get an IDE like eclipse with Maven and testng plugin. 
Go to File->Import and Choose Maven->Existing Maven Projects, click Next.
For the Root Directory, Browse and Choose the applecom_functional_tests that you just cloned. The pom.xml will show in the "Projects" Section.
Make sure the pom.xml is selected and Click Finish.
Your project is now ready for use.
Right click on Prodtestng.xml file and Select Run As->TestNG Suite to run all tests.
You need the chromedriver in the appropriate location, namely "Users/[username]/Documents/SeleniumDriver" to run tests on Chrome on your local machine.
Else Use the Hub with settings as specified in the BrowserConfig file.

CHANGELOG

Current Features: 

	1. Tests Global Nav, Global Footer, Product Nav, Directory Nav, SEO and 404 on all project pages, prototype done for Sochi.
	2. Screenshots utility to take screenshot for all project pages for launch projects
	3. Detailed Test Results Report with logs & Screenshots for failed tests
	4. Can test against any ic branch (todo: Authentication)
	5. Prototype for testing promos
	6. Selenium Grid Setup
	7. Jenkins Setup
	8. Prototype for Search Overlay testing


Upcoming:

	1. Ability to read JavaScript Errors from the Console 
	2. Saving Test Results on a Server and Emailing them
	2. Additional use cases to test viz. Videos Automation, Search Feature
	4. Bug fixes
