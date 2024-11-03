The purpose of a web automation framework with one test that will run in parallel on three different resolutions: 1024 x 768, 1440 x 900, and 1366 x 768.

Framework written in JAVA/gradle, used Page Object Model and Factory Pattern.
POM used to separate logical pages. The functionality of pages and objects IDs/XPaths are in separate files (*Page.java, *Path.java) so maintainance (find/edit) will be more convenient.
There is a "BasePO class" where are wrapped all needed functionaltiy from Selenium, to use further in tests.
There is a "TestBase class" where defined "setUpEach()" where initializing WebDriver with given arguments to tests and "tearDownEach()" functions where quitting created WebDriver for current test.
All tests are inhereted from "TestBase class".
Tests are parameterized with "@CsvSource" tag, where first parameter is WebDriver type (defined in Drivers enum), second and third parameters are resolution.
To run all tests (within one test class) sequentially one need to set "junit.jupiter.execution.parallel.enabled = false" in "junit-platform.properties" file, and for parallel running set "junit.jupiter.execution.parallel.enabled = true".
