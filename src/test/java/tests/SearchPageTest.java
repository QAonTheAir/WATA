package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;
import utils.AssertUtils;
import utils.ConfigReader;

import java.util.List;
import java.util.Map;

public class SearchPageTest extends BaseTest {

    @DataProvider(name = "priceSortData")
    public Object[][] priceSortData() {
        return new Object[][] {
                {"Computers > Notebooks","Price: Low to High", "increase"},
                {"Computers > Notebooks","Price: High to Low", "decrease"}
        };
    }

    @DataProvider(name = "filterDataProductSpec")
    public Object[][] filterDataProductSpec() {
        return new Object[][] {
                {"Computers > Notebooks","Filter by manufacturer", "Apple"},
                {"Computers > Notebooks","CPU Type", "Intel Core i5"},
                {"Computers > Notebooks","Memory", "4 GB"},
                {"Computers > Notebooks","Memory", "8 GB"}
        };
    }
    @DataProvider(name = "filterDataManufacturer")
    public Object[][] filterDataManufacturer() {
        return new Object[][] {
                {"Computers > Notebooks", "Apple"},
                {"Computers > Notebooks", "HP"},
        };
    }
    @DataProvider(name = "filterProductByPrice")
    public Object[][] filterProductByPrice() {
        return new Object[][] {
                {"Computers > Desktops",100,1000}
        };
    }


    @Test
    public void searchTest() {

        HomePage home = new HomePage(driver);
        SearchPage searchPage = new SearchPage(driver);
        // Load values from config file
        String searchKeyword = ConfigReader.getProperty("search.keyword");

        home.sentText(searchPage.txtSearch,searchKeyword);
        home.clickOnButton("Search");
        searchPage.verifyKeywordInAllTitles(searchPage.getAllProductTitles(driver),searchKeyword);

    }
    @Test(dataProvider = "priceSortData")
    public void SortByPriceTest(String menuPath, String dropdownOption, String expectedOrder) {

        HomePage home = new HomePage(driver);
        SearchPage searchPage = new SearchPage(driver);
        home.navigateMenu(menuPath);
        searchPage.selectByVisibleText(searchPage.dropdownProductsOrderBy,dropdownOption);
        AssertUtils.assertPricesOrder(searchPage.convertPricesToNumbers(searchPage.getAllProductPrices(driver)),expectedOrder);

    }

    @Test(dataProvider = "filterDataProductSpec")
    public void FilterTestProductSpecTest(String menuPath, String filterGroup, String filterOption){

        HomePage home = new HomePage(driver);
        SearchPage searchPage = new SearchPage(driver);
        home.navigateMenu(menuPath);
        searchPage.selectFilter(driver,filterGroup, filterOption);
        List<String>  productUrls = searchPage.getAllProductUrls(driver);
        for (String url : productUrls) {
            searchPage.openNewTab(driver, url);
            Map<String, String> specs = searchPage.getProductSpecifications(driver);
            boolean matchFound = false;

            for (String value : specs.values()) {
                if (value.equalsIgnoreCase(filterOption)) {
                    matchFound = true;
                    break;
                }
            }

            Assert.assertTrue(matchFound,
                    "Filter option '" + filterOption + "' not found in product specs on page: " + url);
        }

    }

    @Test(dataProvider = "filterDataManufacturer")
    public void filterDataManufacturerTest(String menuPath, String manufactureName){

        HomePage home = new HomePage(driver);
        SearchPage searchPage = new SearchPage(driver);
        home.navigateMenu(menuPath);
        searchPage.selectManufacturer(driver,manufactureName);
        List<String>  productUrls = searchPage.getAllProductUrls(driver);
        for (String url : productUrls) {
            searchPage.openNewTab(driver, url);
            Map<String,String> actualManufacture = searchPage.getProductManufacturer(driver);
            boolean matchFound = false;

            for (String value : actualManufacture.values()) {
                if (value.equalsIgnoreCase(manufactureName)) {
                    matchFound = true;
                    break;
                }
            }

            Assert.assertTrue(matchFound,
                    "Filter option '" + manufactureName + "' not found in page: " + url);
        }

    }

    @Test(dataProvider = "filterProductByPrice")
    public void filterProductByPriceTest(String menuPath, int minPrice,int maxPrice){

        HomePage home = new HomePage(driver);
        SearchPage searchPage = new SearchPage(driver);
        home.navigateMenu(menuPath);
//        home.setPriceRange(100,1000);
        home.setPriceRangeBySlider(2000,3001);
        List<String>  productUrls = searchPage.getAllProductUrls(driver);
        for (String url : productUrls) {
            searchPage.openNewTab(driver, url);
            List<String> actualPrices = searchPage.getAllProductPrices(driver);
            List<Double> actualPricesNumbers = searchPage.convertPricesToNumbers(actualPrices);
            boolean matchFound = false;

            for (Double value : actualPricesNumbers) {
                if (value >=minPrice && value <=maxPrice){
                    matchFound = true;
                    break;
                }
            }

            Assert.assertTrue(matchFound,
                    "Price is not in range '" + minPrice + "-" + maxPrice + "' not found in page: " + url);
        }

    }


}
