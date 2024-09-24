import { test } from '@playwright/test';
import { URL } from '../model/config'
import { HomePage } from "../model/pages/home.page";
import { LoginPage } from "../model/pages/login.page";


test('Logged In user, Search winery, Write review', async ({ page }) => {
    let homepage = new HomePage(page);
    let loginPage = new LoginPage(page);

    await page.goto(URL.home);

    await homepage.navigateToPage('Профил');
    await loginPage.login("filip@fmail.com", 'verySecurePassword');
    await loginPage.navigateToPage('Почетна');

    await homepage.searchWinery('popov');
    await homepage.selectWineryInResultList(0);
    await homepage.addReview(5, 'Very nice!');
    await homepage.readReviewsAction();
    await homepage.validateReviewsAreDisplayed();
});
