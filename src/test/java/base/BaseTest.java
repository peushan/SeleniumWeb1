package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    public WebDriver driver;

   @BeforeClass
    public void setupDriver(){
        System.setProperty("webdriver.chrome.driver","src/test/resources/chromedriver");
        //ChromeOptions chromeOptions = new ChromeOptions();
       // chromeOptions.setExperimentalOption("useAutomationExtension", false);
       // chromeOptions.addArguments("--remote-debugging-port=9222");
        driver = new ChromeDriver();

    }
}


pipeline {
    agent {
        label "NZLO_Windows_UFT"
    }
    tools{
        maven 'MavenForSelenium'
        jdk 'JDKForSelenium'
    }
    stages {
        stage('mvn clean'){
            steps {
                bat 'mvn clean'
            }
        }
        stage('mvn test'){
            steps {
                catchError(buildResult:'SUCCESS', stageResult:'FAILURE'){
                    bat "mvn -DsuiteXmlFile=${suite_xml_file} -DtestngReportsDirectory=target/test-results/initialrun -DextentReportsDir=/target/ExtentReports/ExtentReportResults_InitialRun.html test"
                }
            }
        }
        stage('retry failed tests'){
            steps{
                bat "mvn -DsuiteXmlFile=target/test-results/initialrun/testng-failed.xml -DtestngReportsDirectory=target/test-results/rerun -DextentReportsDir=/target/ExtentReports/ExtentReportResults_Rerun.html test"
            }
        }
    }
    post{
        always{
            publishHTML target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir:'target/test-results/initialrun',
                    reportFiles:'index.html',
                    reportName:'TestNg Report Initial Run'
                ]
            publishHTML target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir:'target/test-results/rerun',
                    reportFiles:'index.html',
                    reportName:'TestNg Report Re Run'
                ]
            publishHTML target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir:'target/ExtentReports',
                    reportFiles:'ExtentReportResults_InitialRun.html',
                    reportName:'Extent Report Intitial Run'
                ]
            publishHTML target: [
                allowMissing: false,
                alwaysLinkToLastBuild: false,
                keepAll: true,
                reportDir:'target/ExtentReports',
                reportFiles:'ExtentReportResults_Rerun.html',
                reportName:'Extent Report ReRun'
            ]
        }
    }

}
